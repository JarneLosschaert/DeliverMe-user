package be.howest.jarnelosschaert.deliverme.ui.helpers.functions

import be.howest.jarnelosschaert.deliverme.logic.models.Address
import be.howest.jarnelosschaert.deliverme.logic.models.Customer
import java.time.LocalDateTime
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

val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssX")
fun showDate(dateTime: String): String {
    if (dateTime == "") return ""
    val localDateTime = LocalDateTime.parse(dateTime, formatter)
    val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy")
    return localDateTime.format(dateFormatter)
}
fun showTime(dateTime: String): String {
    if (dateTime == "") return ""
    val localDateTime = LocalDateTime.parse(dateTime, formatter)
    val timeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")
    return localDateTime.format(timeFormatter)
}