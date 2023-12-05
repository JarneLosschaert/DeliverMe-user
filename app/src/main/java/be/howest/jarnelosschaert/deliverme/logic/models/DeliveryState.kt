package be.howest.jarnelosschaert.deliverme.logic.models

import com.google.gson.annotations.SerializedName

enum class DeliveryState {
    @SerializedName("paid") PAID,
    @SerializedName("failed") FAILED,
    @SerializedName("assigned") ASSIGNED,
    @SerializedName("transit") TRANSIT,
    @SerializedName("delivered") DELIVERED
}