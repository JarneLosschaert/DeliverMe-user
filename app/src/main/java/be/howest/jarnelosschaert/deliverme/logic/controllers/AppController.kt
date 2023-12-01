package be.howest.jarnelosschaert.deliverme.logic.controllers

import androidx.navigation.NavController
import be.howest.jarnelosschaert.deliverme.helpers.checkAddress
import be.howest.jarnelosschaert.deliverme.logic.AppUiState
import be.howest.jarnelosschaert.deliverme.logic.data.AddressScreenStatus
import be.howest.jarnelosschaert.deliverme.logic.models.Customer
import be.howest.jarnelosschaert.deliverme.logic.models.HomeAddress
import be.howest.jarnelosschaert.deliverme.logic.models.Person
import be.howest.jarnelosschaert.deliverme.ui.BottomNavigationScreens
import be.howest.jarnelosschaert.deliverme.ui.OtherScreens

class AppController(
    private val navController: NavController,
    private val authController: AuthController
) {
    val uiState: AppUiState = AppUiState()
    val dummyCustomers: List<Customer> = listOf(
        Customer(
            id = 1,
            homeAddress = HomeAddress(1, "Street1", "123", "12345", "City1"),
            person = Person(1, "John Doe", "john@example.com", "123456789")
        ),
        Customer(
            id = 2,
            homeAddress = HomeAddress(2, "Street2", "456", "56789", "City2"),
            person = Person(2, "Jane Doe", "jane@example.com", "987654321")
        ),
        // Add more customers as needed
        Customer(
            id = 3,
            homeAddress = HomeAddress(3, "Street3", "789", "98765", "City3"),
            person = Person(3, "Bob Smith", "bob@example.com", "456789123")
        ),
        Customer(
            id = 4,
            homeAddress = HomeAddress(4, "Street4", "101", "54321", "City4"),
            person = Person(4, "Alice Johnson", "alice@example.com", "789123456")
        ),
        Customer(
            id = 5,
            homeAddress = HomeAddress(5, "Street5", "202", "11223", "City5"),
            person = Person(5, "Charlie Brown", "charlie@example.com", "321654987")
        ),
        Customer(
            id = 6,
            homeAddress = HomeAddress(6, "Street6", "303", "33445", "City6"),
            person = Person(6, "Eva Davis", "eva@example.com", "654987321")
        ),
        Customer(
            id = 7,
            homeAddress = HomeAddress(7, "Street7", "404", "55667", "City7"),
            person = Person(7, "Michael White", "michael@example.com", "987321654")
        ),
        Customer(
            id = 8,
            homeAddress = HomeAddress(8, "Street8", "505", "77889", "City8"),
            person = Person(8, "Olivia Miller", "olivia@example.com", "123456789")
        ),
        Customer(
            id = 9,
            homeAddress = HomeAddress(9, "Street9", "606", "99001", "City9"),
            person = Person(9, "David Clark", "david@example.com", "987654321")
        ),
        Customer(
            id = 10,
            homeAddress = HomeAddress(10, "Street10", "707", "11223", "City10"),
            person = Person(10, "Sophia Thomas", "sophia@example.com", "456789123")
        )
        // ...
    )

    init {
        uiState.senderAddress = authController.uiState.customer.homeAddress
        uiState.contacts = dummyCustomers
        uiState.receiver = dummyCustomers[0]
        uiState.receiverAddress = dummyCustomers[0].homeAddress
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