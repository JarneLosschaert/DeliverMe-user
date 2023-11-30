package be.howest.jarnelosschaert.deliverme.logic.services.responses

import be.howest.jarnelosschaert.deliverme.logic.models.Customer
import com.google.gson.annotations.SerializedName

data class RegistrationLoginResponse(
    @SerializedName("jwt") val jwt: String,
    @SerializedName("customer") val customer: Customer
)