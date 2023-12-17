package be.howest.jarnelosschaert.deliverme.logic.services.responses

import android.os.Parcelable
import be.howest.jarnelosschaert.deliverme.logic.models.Customer
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PayResponse(
    @SerializedName("publishableKey") val publishableKey: String,
    @SerializedName("clientSecret") val clientSecret: String
) : Parcelable