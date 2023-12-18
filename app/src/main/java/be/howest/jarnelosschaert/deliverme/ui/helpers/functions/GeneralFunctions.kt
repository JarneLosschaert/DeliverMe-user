package be.howest.jarnelosschaert.deliverme.ui.helpers.functions

import be.howest.jarnelosschaert.deliverme.logic.models.Address
import be.howest.jarnelosschaert.deliverme.logic.models.Customer
import java.time.format.DateTimeFormatter

fun showAddress(address: Address): String {
    return "${address.street} ${address.number},\n${address.zip} ${address.city}"
}

fun showPhoneNumber(phoneNumber: String): String {
    if (phoneNumber.length == 10) {
        return "${phoneNumber.substring(0, 4)} ${phoneNumber.substring(4, 6)} ${phoneNumber.substring(6, 8)} ${phoneNumber.substring(8, 10)}"
    }
    return phoneNumber
}

fun showCustomer(customer: Customer, me: Customer): String {
    return if (customer.id == me.id) {
        "You"
    } else {
        customer.person.name
    }
}

val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy")
val timeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")