package be.howest.jarnelosschaert.deliverme.logic.helpers.notifications

import android.content.Context
import android.util.Log
import androidx.compose.runtime.*
import be.howest.jarnelosschaert.deliverme.logic.models.Delivery
import be.howest.jarnelosschaert.deliverme.logic.models.DeliveryState
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okhttp3.logging.HttpLoggingInterceptor
import java.lang.Math.toRadians

@Composable
fun Notifier(
    context: Context,
    email: String,
    onLocationReceived: (Delivery, LatLng) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    var webSocket by remember { mutableStateOf<WebSocket?>(null) }
    val notificationsManager = NotificationsManager(context)

    var title by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }

    DisposableEffect(context) {
        webSocket = createWebSocket(coroutineScope, email) {
            if (it?.type == "update") {
                when (it.payload.delivery.state) {
                    DeliveryState.delivered -> {
                        title = "Delivery delivered"
                        message = "Your delivery has been delivered!"
                    }
                    DeliveryState.transit -> {
                        title = "Delivery on its way"
                        message = "Your delivery is on its way!"
                    }
                    DeliveryState.assigned -> {
                        title = "Delivery assigned"
                        message = "Your delivery has been assigned!"
                    }
                    else -> {
                        title = "Delivery update"
                        message = "Your delivery has been updated!"
                    }
                }
                notificationsManager.showNotification(title, message, it.payload.delivery)
            } else if (it?.type == "location") {
                Log.d("location", "location update received")
                val location = LatLng(it.payload.lat, it.payload.lon)
                onLocationReceived(it.payload.delivery, location)
                if (areLocationsNearby(
                        it.payload.lat,
                        it.payload.lon,
                        it.payload.delivery.`package`.receiverAddress.lat,
                        it.payload.delivery.`package`.receiverAddress.lon
                    ) && !notificationsManager.isLastMessageSame("Your delivery is almost there!")
                ) {
                    notificationsManager.showNotification(
                        "Delivery close",
                        "Your delivery is almost there!",
                        it.payload.delivery
                    )
                }
            }
        }
        onDispose {
            webSocket?.cancel()
        }
    }
}

fun areLocationsNearby(
    lat1: Double,
    lon1: Double,
    lat2: Double,
    lon2: Double,
    threshold: Double = 1.0
): Boolean {
    val radius = 6371.0

    val dLat = toRadians(lat2 - lat1)
    val dLon = toRadians(lon2 - lon1)

    val a = kotlin.math.sin(dLat / 2) * kotlin.math.sin(dLat / 2) +
            kotlin.math.cos(toRadians(lat1)) * kotlin.math.cos(toRadians(lat2)) *
            kotlin.math.sin(dLon / 2) * kotlin.math.sin(dLon / 2)

    val c = 2 * kotlin.math.atan2(kotlin.math.sqrt(a), kotlin.math.sqrt(1 - a))

    val distance = radius * c

    return distance <= threshold
}

fun createWebSocket(
    coroutineScope: CoroutineScope,
    email: String,
    onMessageReceived: (UpdateEvent?) -> Unit
): WebSocket {

    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    val request = Request.Builder()
        .url("ws:notifier.deliverme.site/ws")
        .build()

    val webSocketListener = object : WebSocketListener() {
        override fun onOpen(webSocket: WebSocket, response: okhttp3.Response) {
            coroutineScope.launch {
                webSocket.send(
                    """
                    {
                        "type": "register",
                        "payload": { "email": "$email" }
                    }
                    """.trimIndent()
                )
            }
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            coroutineScope.launch {
                println(text)
                val updateEvent = try {
                    val json = Json { ignoreUnknownKeys = true }
                    json.decodeFromString(UpdateEvent.serializer(), text)
                } catch (e: Exception) {
                    println("Error decoding: ${e.message}")
                    null
                }
                onMessageReceived(updateEvent)
            }
        }

        override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
            super.onClosed(webSocket, code, reason)
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: okhttp3.Response?) {
            super.onFailure(webSocket, t, response)
        }
    }

    return okHttpClient.newWebSocket(request, webSocketListener)
}