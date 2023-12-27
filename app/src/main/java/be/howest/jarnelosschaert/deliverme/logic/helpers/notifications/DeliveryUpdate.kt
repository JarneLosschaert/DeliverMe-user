package be.howest.jarnelosschaert.deliverme.logic.helpers.notifications

import kotlinx.serialization.Serializable

@Serializable
data class DeliveryUpdate(
    val type: String,
    val payload: PayloadUpdate
)