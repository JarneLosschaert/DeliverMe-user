package be.howest.jarnelosschaert.deliverme.logic

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import be.howest.jarnelosschaert.deliverme.logic.data.*
import be.howest.jarnelosschaert.deliverme.logic.models.PackageSize
import be.howest.jarnelosschaert.deliverme.logic.models.Delivery
import com.google.android.gms.maps.model.LatLng

class AppUiState : ViewModel() {
    var paidDeliveries by mutableStateOf(emptyList<Delivery>())
    var assignedDeliveries by mutableStateOf(emptyList<Delivery>())
    var transitDeliveries by mutableStateOf(emptyList<Delivery>())
    var deliveredDeliveries by mutableStateOf(emptyList<Delivery>())
    var refreshing by mutableStateOf(false)

    var selectedDelivery by mutableStateOf(defaultDelivery)
    var appPackage by mutableStateOf(defaultPackage)
    var payResponse by mutableStateOf(defaultPayResponse)
    var packageErrors by mutableStateOf(emptyList<String>())

    var senderAddress by mutableStateOf(defaultAddress)
    var receiver by mutableStateOf(defaultCustomer)
    var receiverAddress by mutableStateOf(defaultAddress)
    var packageSize by mutableStateOf(PackageSize.small)
    var description by mutableStateOf("")

    var contactsQuery by mutableStateOf("")

    var selectedContact by mutableStateOf(defaultCustomer)

    var addressScreenStatus by mutableStateOf(AddressScreenStatus.SENDER_ADDRESS)
    var addressErrors by mutableStateOf(emptyList<String>())

    var locationDrivers: LatLng? by mutableStateOf(null)

    /*
    fun addLocationDriver(delivery: Delivery, location: LatLng) {
        locationDrivers = locationDrivers + Pair(delivery.id, location)
    }

     */
}