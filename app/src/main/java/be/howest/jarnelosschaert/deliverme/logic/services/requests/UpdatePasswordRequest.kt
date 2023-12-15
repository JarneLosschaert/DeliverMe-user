package be.howest.jarnelosschaert.deliverme.logic.services.requests

import com.google.gson.annotations.SerializedName

data class UpdatePasswordRequest(
    @SerializedName("password") val number: String
)