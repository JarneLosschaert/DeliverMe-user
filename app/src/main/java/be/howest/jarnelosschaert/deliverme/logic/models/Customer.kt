package be.howest.jarnelosschaert.deliverme.logic.models

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Customer(
    val id: Int,
    val homeAddress: Address,
    val person: Person,
    val contacts: List<Customer> = listOf(),
)