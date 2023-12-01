package be.howest.jarnelosschaert.deliverme.logic

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import be.howest.jarnelosschaert.deliverme.logic.data.AddressScreenStatus
import be.howest.jarnelosschaert.deliverme.logic.data.PackageSize
import be.howest.jarnelosschaert.deliverme.logic.models.Customer
import be.howest.jarnelosschaert.deliverme.logic.models.HomeAddress
import be.howest.jarnelosschaert.deliverme.logic.models.Person

class AppUiState : ViewModel() {

    var contacts by mutableStateOf(emptyList<Customer>())

    var senderAddress by mutableStateOf(HomeAddress(-1, "", "", "", ""))
    var receiver by mutableStateOf(Customer(-1, HomeAddress(-1, "", "", "", ""), Person(-1, "", "", "")))
    var receiverAddress by mutableStateOf(HomeAddress(-1, "", "", "", ""))
    var packageSize by mutableStateOf(PackageSize.SMALL)
    var description by mutableStateOf("")

    var addressScreenStatus by mutableStateOf(AddressScreenStatus.SENDER_ADDRESS)
    var addressErrors by mutableStateOf(emptyList<String>())
}