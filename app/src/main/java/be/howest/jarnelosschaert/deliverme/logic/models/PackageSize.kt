package be.howest.jarnelosschaert.deliverme.logic.models

import kotlinx.serialization.Serializable

@Serializable
enum class PackageSize(val value: String) {
    letter("Letter"),
    small("Small"),
    medium("Medium"),
    large("Large"),
}