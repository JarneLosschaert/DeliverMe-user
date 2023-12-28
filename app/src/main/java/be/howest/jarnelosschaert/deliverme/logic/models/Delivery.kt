package be.howest.jarnelosschaert.deliverme.logic.models

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class Delivery(
    val id: Int,
    val dateTimeDeparted: String,
    val dateTimeArrived: String,
    val state: DeliveryState,
    val `package`: Package,
    val driver: Driver
)