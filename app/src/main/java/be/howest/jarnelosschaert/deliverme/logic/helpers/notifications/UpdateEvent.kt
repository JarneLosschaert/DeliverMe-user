package be.howest.jarnelosschaert.deliverme.logic.helpers.notifications

import kotlinx.serialization.Serializable

@Serializable
data class UpdateEvent(
    val type: String,
    val payload: Payload
)