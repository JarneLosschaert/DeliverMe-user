package be.howest.jarnelosschaert.deliverme.logic.models

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
enum class PackageSize(val value: String) {
    @SerializedName("letter")
    LETTER("letter"),

    @SerializedName("small")
    SMALL("small"),

    @SerializedName("medium")
    MEDIUM("medium"),

    @SerializedName("large")
    LARGE("large"),
}