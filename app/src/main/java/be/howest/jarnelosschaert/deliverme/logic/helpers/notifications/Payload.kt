package be.howest.jarnelosschaert.deliverme.logic.helpers.notifications

import be.howest.jarnelosschaert.deliverme.logic.models.Delivery
import kotlinx.serialization.Serializable

@Serializable
data class Payload(
    val lat: Double = 0.0,
    val lon: Double = 0.0,
    val delivery: Delivery
)