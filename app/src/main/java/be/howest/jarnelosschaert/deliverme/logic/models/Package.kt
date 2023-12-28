package be.howest.jarnelosschaert.deliverme.logic.models

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Package(
    val id: Int,
    val sender: Customer,
    val receiver: Customer,
     val senderAddress: Address,
    val receiverAddress: Address,
    val packageSize: PackageSize,
    val description: String,
    val fee: Double
)