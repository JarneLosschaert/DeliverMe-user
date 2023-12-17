package be.howest.jarnelosschaert.deliverme.logic.models

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class Delivery(
    @SerializedName("id") val id: Int,
    @SerializedName("dateTimeDeparted") val dateTimeDeparted: LocalDateTime?,
    @SerializedName("dateTimeArrived") val dateTimeArrived: LocalDateTime?,
    @SerializedName("state") val state: DeliveryState,
    @SerializedName("package") val packageInfo: Package
)