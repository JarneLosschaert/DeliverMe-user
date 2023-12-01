package be.howest.jarnelosschaert.deliverme.logic.models

import com.google.gson.annotations.SerializedName

data class HomeAddress(
    @SerializedName("id") val id: Int,
    @SerializedName("street") val street: String,
    @SerializedName("number") val number: String,
    @SerializedName("zip") val zip: String,
    @SerializedName("city") val city: String,
    )