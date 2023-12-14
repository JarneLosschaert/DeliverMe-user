package be.howest.jarnelosschaert.deliverme.logic.services.requests

import be.howest.jarnelosschaert.deliverme.logic.models.Address
import be.howest.jarnelosschaert.deliverme.logic.models.PackageSize
import com.google.gson.annotations.SerializedName

data class CreatePackageRequest(
    @SerializedName("receiverId") val receiverId: Int,
    @SerializedName("senderAddress") val senderAddress: Address,
    @SerializedName("receiverAddress") val receiverAddress: Address,
    @SerializedName("packageSize") val packageSize: PackageSize,
    @SerializedName("description") val description: String
)
