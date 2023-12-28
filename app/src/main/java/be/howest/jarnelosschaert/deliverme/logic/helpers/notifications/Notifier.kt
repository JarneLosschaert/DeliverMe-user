package be.howest.jarnelosschaert.deliverme.logic.helpers.notifications

import android.content.Context
import androidx.compose.runtime.*
import be.howest.jarnelosschaert.deliverme.logic.models.DeliveryState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okhttp3.logging.HttpLoggingInterceptor

@Composable
fun Notifier(
    context: Context,
    email: String,
) {
    val coroutineScope = rememberCoroutineScope()
    var webSocket by remember { mutableStateOf<WebSocket?>(null) }
    val notificationsManager = NotificationsManager(context)

    var title by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }

    DisposableEffect(context) {
        webSocket = createWebSocket(coroutineScope, email) {
            when (it?.payload?.delivery?.state) {
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
            notificationsManager.showNotification(title, message)
        }
        onDispose {
            webSocket?.cancel()
        }
    }
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
        .url("ws://192.168.0.191:5000/ws")
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

fun sendMessage(webSocket: WebSocket?, message: String) {
    webSocket?.send(message)
}