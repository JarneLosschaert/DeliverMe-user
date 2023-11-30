package be.howest.jarnelosschaert.deliverme.logic.services.requests

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @SerializedName("Name") val username: String,
    @SerializedName("Email") val email: String,
    @SerializedName("Phone") val phone: String,
    @SerializedName("Password") val password: String,
    @SerializedName("Street") val street: String,
    @SerializedName("City") val city: String,
    @SerializedName("Zip") val zip: String,
    @SerializedName("Number") val number: String
)

data class RegistrationResponse(
    @SerializedName("jwt") val jwt: String,
    @SerializedName("customer") val customer: Customer
)

data class Customer(
    @SerializedName("id") val id: Int,
    @SerializedName("homeAddress") val homeAddress: HomeAddress,
    @SerializedName("person") val person: Person
)

data class HomeAddress(
    @SerializedName("id") val id: Int,
    @SerializedName("street") val street: String,
    @SerializedName("city") val city: String,
    @SerializedName("zip") val zip: String,
    @SerializedName("number") val number: String
)

data class Person(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("phone") val phone: String
)
