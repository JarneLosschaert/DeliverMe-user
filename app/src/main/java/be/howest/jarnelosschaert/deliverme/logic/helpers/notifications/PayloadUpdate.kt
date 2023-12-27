package be.howest.jarnelosschaert.deliverme.logic.helpers.notifications

import be.howest.jarnelosschaert.deliverme.logic.models.Delivery
import kotlinx.serialization.Serializable

@Serializable
data class PayloadUpdate(
    val delivery: Delivery
)