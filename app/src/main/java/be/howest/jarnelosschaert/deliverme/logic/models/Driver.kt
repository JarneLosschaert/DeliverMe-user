package be.howest.jarnelosschaert.deliverme.logic.models

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Driver(
    val id: Int,
    val walletAddress: String,
    val person: Person,
)