package be.howest.jarnelosschaert.deliverme.logic.controllers

import androidx.navigation.NavController
import be.howest.jarnelosschaert.deliverme.helpers.checkAddress
import be.howest.jarnelosschaert.deliverme.logic.AppUiState
import be.howest.jarnelosschaert.deliverme.logic.data.AddressScreenStatus
import be.howest.jarnelosschaert.deliverme.logic.data.dummyContacts
import be.howest.jarnelosschaert.deliverme.logic.data.dummyDeliveries
import be.howest.jarnelosschaert.deliverme.logic.models.*
import be.howest.jarnelosschaert.deliverme.logic.services.PackagesService
import be.howest.jarnelosschaert.deliverme.ui.BottomNavigationScreens
import be.howest.jarnelosschaert.deliverme.ui.OtherScreens

class AppController(
    private val navController: NavController,
    private val authController: AuthController
) {
    val uiState: AppUiState = AppUiState()
    val packagesService = PackagesService()

    init {
        uiState.activeDeliveries = dummyDeliveries.filter { it.state == DeliveryState.ASSIGNED }
        uiState.pastDeliveries = dummyDeliveries.filter { it.state == DeliveryState.DELIVERED }
        uiState.senderAddress = authController.uiState.customer.homeAddress
        uiState.receiver = authController.uiState.customer
        uiState.receiverAddress = authController.uiState.customer.homeAddress
        getPackages()
    }

    fun getPackages() {
        packagesService.getPackages(
            authController.uiState.jwt,
            handleSuccess = { packages ->
                println("test")
                println(packages[0].name)
            },
            handleFailure = { message ->
                println(message)
            }
        )
    }

    fun createPackage() {
        packagesService.createPackage(
            authController.uiState.jwt,
            uiState.receiver.id,
            uiState.senderAddress,
            uiState.receiverAddress,
            uiState.packageSize,
            uiState.description,
            handleSuccess = { message ->
                println(message)
            },
            handleFailure = { message ->
                println(message)
            }
        )
    }

    fun onDeliveryClicked(delivery : Delivery) {
        uiState.delivery = delivery
        navigateTo(OtherScreens.DeliveryDetails.route)
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

    fun onConfirmAddress(address: Address) {
        val errors = checkAddress(address)
        uiState.addressErrors = errors
        if (errors.isEmpty()) {
            when (uiState.addressScreenStatus) {
                AddressScreenStatus.SENDER_ADDRESS -> {
                    uiState.senderAddress = address
                }
                AddressScreenStatus.RECEIVER_ADDRESS -> {
                    uiState.receiverAddress = address
                }
            }
            goBack()
        }
    }

    fun getContacts(): List<Customer> {
        val queryLowerCase = uiState.contactsQuery.lowercase()
        return authController.uiState.customer.contacts.filter {
            it.person.name.lowercase().contains(queryLowerCase, true) ||
            it.person.phone.lowercase().contains(queryLowerCase, true) ||
            it.person.email.lowercase().contains(queryLowerCase, true) ||
            it.homeAddress.street.lowercase().contains(queryLowerCase, true) ||
            it.homeAddress.city.lowercase().contains(queryLowerCase, true)
        }
    }

    fun onContactClick(customer: Customer) {
        uiState.contact = customer
        navigateTo(OtherScreens.Contact.route)
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