package be.howest.jarnelosschaert.deliverme.logic.models

import com.google.gson.annotations.SerializedName

data class Delivery(
    @SerializedName("id") val id: Int,
    @SerializedName("dateTimeDeparted") val dateTimeDeparted: String?, // Use a proper date/time type if available
    @SerializedName("dateTimeArrived") val dateTimeArrived: String?, // Use a proper date/time type if available
    @SerializedName("state") val state: DeliveryState,
    @SerializedName("package") val packageInfo: Package
)