package be.howest.jarnelosschaert.deliverme.logic.controllers

import androidx.navigation.NavController
import be.howest.jarnelosschaert.deliverme.helpers.checkAddress
import be.howest.jarnelosschaert.deliverme.logic.AppUiState
import be.howest.jarnelosschaert.deliverme.logic.data.AddressScreenStatus
import be.howest.jarnelosschaert.deliverme.logic.data.dummyContacts
import be.howest.jarnelosschaert.deliverme.logic.models.Customer
import be.howest.jarnelosschaert.deliverme.logic.models.HomeAddress
import be.howest.jarnelosschaert.deliverme.ui.BottomNavigationScreens
import be.howest.jarnelosschaert.deliverme.ui.OtherScreens

class AppController(
    private val navController: NavController,
    private val authController: AuthController
) {
    val uiState: AppUiState = AppUiState()

    init {
        uiState.senderAddress = authController.uiState.customer.homeAddress
        uiState.contacts = dummyContacts
        uiState.receiver = dummyContacts[0]
        uiState.receiverAddress = dummyContacts[0].homeAddress
    }

    fun onSenderAddressChange() {
        uiState.addressScreenStatus = AddressScreenStatus.SENDER_ADDRESS
        navigateTo(OtherScreens.Address.route)
    }

    fun onReceiverAddressChange() {
        uiState.addressScreenStatus = AddressScreenStatus.RECEIVER_ADDRESS
        navigateTo(OtherScreens.Address.route)
    }

    fun onReceiverChange(customer: Customer) {
        uiState.receiver = customer
        uiState.receiverAddress = customer.homeAddress
    }

    fun onConfirmAddress(homeAddress: HomeAddress) {
        val errors = checkAddress(homeAddress)
        uiState.addressErrors = errors
        if (errors.isEmpty()) {
            when (uiState.addressScreenStatus) {
                AddressScreenStatus.SENDER_ADDRESS -> {
                    uiState.senderAddress = homeAddress
                }
                AddressScreenStatus.RECEIVER_ADDRESS -> {
                    uiState.receiverAddress = homeAddress
                }
            }
            goBack()
        }
    }

    fun getContacts(): List<Customer> {
        val queryLowerCase = uiState.contactsQuery.lowercase()
        return uiState.contacts.filter {
            it.person.name.lowercase().contains(queryLowerCase, true) ||
            it.person.phone.lowercase().contains(queryLowerCase, true) ||
            it.person.email.lowercase().contains(queryLowerCase, true) ||
            it.homeAddress.street.lowercase().contains(queryLowerCase, true) ||
            it.homeAddress.city.lowercase().contains(queryLowerCase, true)
        }
    }

    fun goBack() {
        if (navController.currentDestination?.route == BottomNavigationScreens.Home.route) {
            //uiState.showExitDialog = true
        } else {
            navController.popBackStack()
        }
    }

    fun navigateTo(route: String) {
        navController.navigate(route)
    }

}