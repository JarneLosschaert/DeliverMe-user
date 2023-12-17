package be.howest.jarnelosschaert.deliverme.logic

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import be.howest.jarnelosschaert.deliverme.logic.data.*
import be.howest.jarnelosschaert.deliverme.logic.models.PackageSize
import be.howest.jarnelosschaert.deliverme.logic.models.Customer
import be.howest.jarnelosschaert.deliverme.logic.models.Delivery
import com.stripe.android.paymentsheet.PaymentSheet

class AppUiState : ViewModel() {
    var activeDeliveries by mutableStateOf(emptyList<Delivery>())
    var pastDeliveries by mutableStateOf(emptyList<Delivery>())

    var delivery by mutableStateOf(defaultDelivery)
    var appPackage by mutableStateOf(defaultPackage)
    var payResponse by mutableStateOf(defaultPayResponse)

    var senderAddress by mutableStateOf(defaultAddress)
    var receiver by mutableStateOf(defaultCustomer)
    var receiverAddress by mutableStateOf(defaultAddress)
    var packageSize by mutableStateOf(PackageSize.SMALL)
    var description by mutableStateOf("")

    var contactsQuery by mutableStateOf("")

    var contact by mutableStateOf(defaultCustomer)

    var addressScreenStatus by mutableStateOf(AddressScreenStatus.SENDER_ADDRESS)
    var addressErrors by mutableStateOf(emptyList<String>())
}