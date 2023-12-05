package be.howest.jarnelosschaert.deliverme.logic.models

import com.google.gson.annotations.SerializedName

enum class PackageSize {
    @SerializedName("letter") LETTER,
    @SerializedName("small") SMALL,
    @SerializedName("medium") MEDIUM,
    @SerializedName("large") LARGE;

    override fun toString(): String = name.lowercase()
}