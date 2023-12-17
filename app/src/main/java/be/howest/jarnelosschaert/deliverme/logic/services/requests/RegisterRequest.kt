package be.howest.jarnelosschaert.deliverme.logic.services.requests

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @SerializedName("Name") val username: String,
    @SerializedName("Email") val email: String,
    @SerializedName("Phone") val phone: String,
    @SerializedName("Password") val password: String,
    @SerializedName("Street") val street: String,
    @SerializedName("Number") val number: String,
    @SerializedName("Zip") val zip: String,
    @SerializedName("City") val city: String,
)
