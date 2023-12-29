package be.howest.jarnelosschaert.deliverme.logic.helpers.notifications

import kotlinx.serialization.Serializable

@Serializable
data class Location(
    val latitude: Double,
    val longitude: Double
)