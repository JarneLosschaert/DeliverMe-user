package be.howest.jarnelosschaert.deliverme.logic.controllers

import android.app.Activity
import android.content.RestrictionsManager
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.navigation.NavController
import be.howest.jarnelosschaert.deliverme.logic.AppUiState
import be.howest.jarnelosschaert.deliverme.logic.data.AddressScreenStatus
import be.howest.jarnelosschaert.deliverme.logic.data.defaultContact
import be.howest.jarnelosschaert.deliverme.logic.helpers.checkAddress
import be.howest.jarnelosschaert.deliverme.logic.helpers.checkPackage
import be.howest.jarnelosschaert.deliverme.logic.models.Address
import be.howest.jarnelosschaert.deliverme.logic.models.Customer
import be.howest.jarnelosschaert.deliverme.logic.models.Delivery
import be.howest.jarnelosschaert.deliverme.logic.models.DeliveryState
import be.howest.jarnelosschaert.deliverme.logic.services.AuthService
import be.howest.jarnelosschaert.deliverme.logic.services.DeliveriesService
import be.howest.jarnelosschaert.deliverme.ui.BottomNavigationScreens
import be.howest.jarnelosschaert.deliverme.ui.OtherScreens

class AppController(
    private val navController: NavController,
    private val authController: AuthController
) {
    val uiState: AppUiState = AppUiState()
    private val authService = AuthService()
    private val deliveriesService = DeliveriesService()

    init {
        loadDeliveries()
        uiState.senderAddress = authController.uiState.customer.homeAddress
        if (authController.uiState.customer.contacts.isNotEmpty()) {
            uiState.receiver = authController.uiState.customer.contacts[0]
            uiState.receiverAddress = authController.uiState.customer.contacts[0].homeAddress
        } else {
            uiState.receiver = defaultContact
            uiState.receiverAddress = defaultContact.homeAddress
        }
    }


    fun loadDeliveries(refreshing: Boolean = false) {
        if (refreshing) uiState.refreshing = true
        deliveriesService.getDeliveries(
            authController.uiState.jwt,
            handleSuccess = { deliveries ->
                uiState.paidDeliveries =
                    deliveries.filter { it.state == DeliveryState.paid }
                uiState.assignedDeliveries =
                    deliveries.filter { it.state == DeliveryState.assigned }
                uiState.transitDeliveries =
                    deliveries.filter { it.state == DeliveryState.transit }
                uiState.deliveredDeliveries =
                    deliveries.filter { it.state == DeliveryState.delivered }
                uiState.refreshing = false
                if (refreshing) Toast.makeText(
                    navController.context,
                    "Deliveries up to date",
                    Toast.LENGTH_SHORT
                ).show()
            },
            handleFailure = {
                uiState.refreshing = false
            }
        )
    }

    fun createPackage() {
        clearErrors()
        uiState.packageErrors = checkPackage(uiState.description)
        if (uiState.packageErrors.isEmpty()) {
            deliveriesService.createPackage(
                authController.uiState.jwt,
                uiState.receiver.id,
                uiState.senderAddress,
                uiState.receiverAddress,
                uiState.packageSize,
                uiState.description,
                handleSuccess = { newPackage ->
                    uiState.appPackage = newPackage
                    getPayInfo()
                },
                handleFailure = { message ->
                    Toast.makeText(navController.context, message, Toast.LENGTH_SHORT).show()
                }
            )
            clearErrors()
        }
    }

    private fun getPayInfo() {
        deliveriesService.getPaymentIntent(
            authController.uiState.jwt,
            uiState.appPackage.id,
            handleSuccess = { payResponse ->
                uiState.payResponse = payResponse
                navigateTo(OtherScreens.Pay.route)
            },
            handleFailure = { message ->
                Toast.makeText(navController.context, message, Toast.LENGTH_SHORT).show()
            }
        )
    }

    fun onPay(activityResult: ActivityResult) {
        when (activityResult.resultCode) {
            Activity.RESULT_OK -> {
                Toast.makeText(navController.context, "Payment successful", Toast.LENGTH_SHORT)
                    .show()
                navigateTo(BottomNavigationScreens.Home.route)
            }
            Activity.RESULT_CANCELED -> {
                Toast.makeText(navController.context, "Payment canceled", Toast.LENGTH_SHORT).show()
            }
            RestrictionsManager.RESULT_ERROR -> {
                Toast.makeText(navController.context, "Payment error", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun onDeliveryClicked(delivery: Delivery) {
        uiState.selectedDelivery = delivery
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

    fun getContactDeliveries(): List<Delivery> {
        val deliveries =
            uiState.paidDeliveries + uiState.assignedDeliveries + uiState.transitDeliveries + uiState.deliveredDeliveries
        return deliveries.filter {
            it.`package`.sender.id == uiState.selectedContact.id ||
                    it.`package`.receiver.id == uiState.selectedContact.id
        }
    }

    fun onConfirmAddress(address: Address) {
        clearErrors()
        uiState.addressErrors = checkAddress(address)
        if (uiState.addressErrors.isEmpty()) {
            when (uiState.addressScreenStatus) {
                AddressScreenStatus.SENDER_ADDRESS -> {
                    uiState.senderAddress = address
                }
                AddressScreenStatus.RECEIVER_ADDRESS -> {
                    uiState.receiverAddress = address
                }
                AddressScreenStatus.CUSTOMER_EDIT_ADDRESS -> {
                    authController.updateCustomer(address = address)
                }
            }
            goBack()
            clearErrors()
        }
    }

    fun changeAddress() {
        uiState.addressScreenStatus = AddressScreenStatus.CUSTOMER_EDIT_ADDRESS
        navigateTo(OtherScreens.Address.route)
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

    fun addContact(email: String) {
        authService.addContact(authController.uiState.jwt, email,
            handleSuccess = { customer ->
                authController.uiState.customer = customer
                Toast.makeText(
                    navController.context,
                    "Added ${customer.person.name}",
                    Toast.LENGTH_LONG
                ).show()
            }, handleFailure = {
                Toast.makeText(navController.context, "Adding contact failed", Toast.LENGTH_LONG)
                    .show()
            })
    }

    fun deleteContact() {
        authService.deleteContact(authController.uiState.jwt, uiState.selectedContact.id,
            handleSuccess = {
                authController.uiState.customer =
                    authController.uiState.customer.copy(contacts = authController.uiState.customer.contacts.filter { it.id != uiState.selectedContact.id })
                goBack()
                Toast.makeText(
                    navController.context,
                    "Deleted ${uiState.selectedContact.person.name}",
                    Toast.LENGTH_LONG
                ).show()
            }, handleFailure = { message ->
                println(message)
                Toast.makeText(
                    navController.context,
                    "Deleting ${uiState.selectedContact.person.name} failed",
                    Toast.LENGTH_LONG
                ).show()
            })
    }

    fun onContactClick(customer: Customer) {
        uiState.selectedContact = customer
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

    private fun clearErrors() {
        uiState.packageErrors = emptyList()
        uiState.addressErrors = emptyList()
    }
}