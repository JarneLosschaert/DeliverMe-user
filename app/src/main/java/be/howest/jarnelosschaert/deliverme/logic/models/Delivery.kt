package be.howest.jarnelosschaert.deliverme.logic.models

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class Delivery(
    @SerializedName("id") val id: Int,
    @SerializedName("dateTimeDeparted") val dateTimeDeparted: String,
    @SerializedName("dateTimeArrived") val dateTimeArrived: String,
    @SerializedName("state") val state: DeliveryState,
    @SerializedName("package") val packageInfo: Package
)