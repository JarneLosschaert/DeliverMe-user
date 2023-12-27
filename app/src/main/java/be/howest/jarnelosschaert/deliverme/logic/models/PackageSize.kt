package be.howest.jarnelosschaert.deliverme.logic.models

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
enum class PackageSize(val value: String) {
    @SerializedName("letter")
    LETTER("Letter"),

    @SerializedName("small")
    SMALL("Small"),

    @SerializedName("medium")
    MEDIUM("Medium"),

    @SerializedName("large")
    LARGE("Large"),
}