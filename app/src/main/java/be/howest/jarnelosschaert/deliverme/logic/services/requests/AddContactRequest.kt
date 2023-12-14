package be.howest.jarnelosschaert.deliverme.logic.services.requests

import com.google.gson.annotations.SerializedName

data class AddContactRequest(
    @SerializedName("email") val email: String,
)