package be.howest.jarnelosschaert.deliverme.ui.helpers.functions

fun showAddress(street: String, number: String, zip: String, city: String): String {
    return "$street $number, $zip $city"
}