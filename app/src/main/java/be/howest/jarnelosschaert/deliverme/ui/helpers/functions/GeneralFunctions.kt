package be.howest.jarnelosschaert.deliverme.ui.helpers.functions

import be.howest.jarnelosschaert.deliverme.logic.models.Address

fun showAddress(address: Address): String {
    return "${address.street} ${address.number}, ${address.zip} ${address.city}"
}

fun showPhoneNumber(phoneNumber: String): String {
    if (phoneNumber.length == 10) {
        return "${phoneNumber.substring(0, 4)} ${phoneNumber.substring(4, 6)} ${phoneNumber.substring(6, 8)} ${phoneNumber.substring(8, 10)}"
    }
    return phoneNumber
}