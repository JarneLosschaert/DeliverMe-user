package be.howest.jarnelosschaert.deliverme.logic.models

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
enum class DeliveryState(val value: String) {
    @SerializedName("paid") PAID("Delivery paid.\nSearching for driver..."),
    @SerializedName("failed") FAILED("Delivery failed."),
    @SerializedName("assigned") ASSIGNED("A driver has accepted your package.\nWaiting for pickup..."),
    @SerializedName("transit") TRANSIT("Your package is on its way!"),
    @SerializedName("delivered") DELIVERED("Your package has been delivered."),
}