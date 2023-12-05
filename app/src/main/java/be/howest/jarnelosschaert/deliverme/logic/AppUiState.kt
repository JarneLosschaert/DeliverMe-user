package be.howest.jarnelosschaert.deliverme.logic

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import be.howest.jarnelosschaert.deliverme.logic.data.AddressScreenStatus
import be.howest.jarnelosschaert.deliverme.logic.models.PackageSize
import be.howest.jarnelosschaert.deliverme.logic.data.defaultAddress
import be.howest.jarnelosschaert.deliverme.logic.data.defaultCustomer
import be.howest.jarnelosschaert.deliverme.logic.models.Customer

class AppUiState : ViewModel() {

    var contacts by mutableStateOf(emptyList<Customer>())
    var contactsQuery by mutableStateOf("")

    var contact by mutableStateOf(defaultCustomer)

    var senderAddress by mutableStateOf(defaultAddress)
    var receiver by mutableStateOf(defaultCustomer)
    var receiverAddress by mutableStateOf(defaultAddress)
    var packageSize by mutableStateOf(PackageSize.SMALL)
    var description by mutableStateOf("")

    var addressScreenStatus by mutableStateOf(AddressScreenStatus.SENDER_ADDRESS)
    var addressErrors by mutableStateOf(emptyList<String>())
}