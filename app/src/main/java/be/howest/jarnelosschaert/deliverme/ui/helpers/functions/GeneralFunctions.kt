package be.howest.jarnelosschaert.deliverme.ui.helpers.functions

import be.howest.jarnelosschaert.deliverme.logic.models.HomeAddress

fun showAddress(homeAddress: HomeAddress): String {
    return "${homeAddress.street} ${homeAddress.number}, ${homeAddress.zip} ${homeAddress.city}"
}

fun showPhoneNumber(phoneNumber: String): String {
    if (phoneNumber.length == 10) {
        return "${phoneNumber.substring(0, 4)} ${phoneNumber.substring(4, 6)} ${phoneNumber.substring(6, 8)} ${phoneNumber.substring(8, 10)}"
    }
    return phoneNumber
}