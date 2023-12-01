package be.howest.jarnelosschaert.deliverme.ui.helpers.functions

import be.howest.jarnelosschaert.deliverme.logic.models.HomeAddress

fun showAddress(homeAddress: HomeAddress): String {
    return "${homeAddress.street} ${homeAddress.number}, ${homeAddress.zip} ${homeAddress.city}"
}