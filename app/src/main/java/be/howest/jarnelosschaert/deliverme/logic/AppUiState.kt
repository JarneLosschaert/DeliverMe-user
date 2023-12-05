package be.howest.jarnelosschaert.deliverme.logic

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import be.howest.jarnelosschaert.deliverme.logic.data.AddressScreenStatus
import be.howest.jarnelosschaert.deliverme.logic.models.PackageSize
import be.howest.jarnelosschaert.deliverme.logic.data.defaultAddress
import be.howest.jarnelosschaert.deliverme.logic.data.defaultCustomer
import be.howest.jarnelosschaert.deliverme.logic.data.defaultDelivery
import be.howest.jarnelosschaert.deliverme.logic.models.Customer
import be.howest.jarnelosschaert.deliverme.logic.models.Delivery

class AppUiState : ViewModel() {
    var activeDeliveries by mutableStateOf(emptyList<Delivery>())
    var pastDeliveries by mutableStateOf(emptyList<Delivery>())

    var delivery by mutableStateOf(defaultDelivery)

    var senderAddress by mutableStateOf(defaultAddress)
    var receiver by mutableStateOf(defaultCustomer)
    var receiverAddress by mutableStateOf(defaultAddress)
    var packageSize by mutableStateOf(PackageSize.SMALL)
    var description by mutableStateOf("")

    var contacts by mutableStateOf(emptyList<Customer>())
    var contactsQuery by mutableStateOf("")

    var contact by mutableStateOf(defaultCustomer)

    var addressScreenStatus by mutableStateOf(AddressScreenStatus.SENDER_ADDRESS)
    var addressErrors by mutableStateOf(emptyList<String>())
}