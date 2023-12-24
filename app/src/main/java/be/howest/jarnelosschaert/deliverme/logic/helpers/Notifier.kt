import android.content.Context
import androidx.compose.runtime.*
import be.howest.jarnelosschaert.deliverme.logic.helpers.NotificationManager
import be.howest.jarnelosschaert.deliverme.logic.models.Address
import be.howest.jarnelosschaert.deliverme.logic.models.Delivery
import be.howest.jarnelosschaert.deliverme.logic.models.DeliveryState
import be.howest.jarnelosschaert.deliverme.logic.models.Person
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
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
    val notificationManager = NotificationManager(context)

    var title by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }

    DisposableEffect(context) {
        webSocket = createWebSocket(coroutineScope, email) {
            when (it?.payload?.delivery?.state) {
                DeliveryState.DELIVERED -> {
                    title = "Delivery delivered"
                    message = "Your delivery has been delivered!"
                }
                DeliveryState.TRANSIT -> {
                    title = "Delivery on its way"
                    message = "Your delivery is on its way!"
                }
                DeliveryState.ASSIGNED -> {
                    title = "Delivery assigned"
                    message = "Your delivery has been assigned!"
                }
                else -> {
                    title = "Delivery update"
                    message = "Your delivery has been updated!"
                }
            }
            notificationManager.showNotification(title, message)
        }
        onDispose {
            webSocket?.cancel()
        }
    }
}

fun createWebSocket(
    coroutineScope: CoroutineScope,
    email: String,
    onMessageReceived: (DeliveryUpdate?) -> Unit
): WebSocket {

    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    val request = Request.Builder()
        .url("ws://192.168.1.20:5000/ws")
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
                val deliveryUpdate = try {
                    Json.decodeFromString(DeliveryUpdate.serializer(), text)
                } catch (e: Exception) {
                    println("Error: ${e.message}")
                    null
                }
                onMessageReceived(deliveryUpdate)
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

@Serializable
data class DeliveryUpdate(
    val type: String,
    val payload: UpdatePayload
)

@Serializable
data class UpdatePayload(
    val delivery: Delivery
)

data class Customer(
    @SerializedName("id") val id: Int,
    @SerializedName("homeAddress") val homeAddress: Address,
    @SerializedName("person") val person: Person,
    @SerializedName("contacts") val contacts: List<Customer>,
)