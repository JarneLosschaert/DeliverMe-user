package be.howest.jarnelosschaert.deliverme.logic.models

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Customer(
    @SerializedName("id") val id: Int,
    @SerializedName("homeAddress") val homeAddress: Address,
    @SerializedName("person") val person: Person,
    @SerializedName("contacts") val contacts: List<Customer>,
)