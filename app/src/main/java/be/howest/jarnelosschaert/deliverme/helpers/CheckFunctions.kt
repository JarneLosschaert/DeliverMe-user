package be.howest.jarnelosschaert.deliverme.helpers

import be.howest.jarnelosschaert.deliverme.logic.models.HomeAddress

fun checkValuesSignUp(
    username: String,
    email: String,
    phone: String,
    password: String,
    confirmPassword: String
): List<String> {
    val errors = mutableListOf<String>()

    if (username.isBlank()) {
        errors.add("Username is required.")
    }

    if (email.isBlank()) {
        errors.add("Email is required.")
    } else if (!isValidEmail(email)) {
        errors.add("Invalid email format.")
    }

    if (phone.isBlank()) {
        errors.add("Phone number is required.")
    } else if (!isValidPhoneNumber(phone)) {
        errors.add("Invalid phone number format (ex: 0499999999).")
    }

    if (password.isBlank()) {
        errors.add("Password is required.")
    } else if (password.length < 8) {
        errors.add("Password must be at least 8 characters long.")
    } else if (password != confirmPassword) {
        errors.add("Passwords do not match.")
    }

    return errors
}

private fun isValidEmail(email: String): Boolean {
    val emailRegex = "^[A-Za-z](.*)(@)(.+)(\\.)(.+)"
    return email.matches(emailRegex.toRegex())
}

private fun isValidPhoneNumber(phone: String): Boolean {
    val cleanedPhoneNumber = phone.replace(Regex("\\D"), "")
    return cleanedPhoneNumber.startsWith("0") && cleanedPhoneNumber.length == 10
}

fun checkAddress(HomeAddress: HomeAddress): List<String> {
    val errors = mutableListOf<String>()

    if (HomeAddress.street.isBlank()) {
        errors.add("Street is required.")
    } else if (!isValidStreet(HomeAddress.street)) {
        errors.add("Invalid street format.")
    }

    if (HomeAddress.number.isBlank()) {
        errors.add("Number is required.")
    } else if (!isValidNumber(HomeAddress.number)) {
        errors.add("Invalid number format.")
    }

    if (HomeAddress.zip.isBlank()) {
        errors.add("Zip code is required.")
    } else if (!isValidPostalCode(HomeAddress.zip)) {
        errors.add("Invalid zip code format.")
    }

    if (HomeAddress.city.isBlank()) {
        errors.add("City is required.")
    } else if (!isValidCity(HomeAddress.city)) {
        errors.add("Invalid city format.")
    }

    return errors
}

private fun isValidPostalCode(zip: String): Boolean {
    val zipRegex = "^[1-9]\\d{3}$"
    return zip.matches(zipRegex.toRegex())
}

private fun isValidStreet(street: String): Boolean {
    
    val streetRegex = "^[A-Za-z]+(?:[\\s-][A-Za-z]+)*\$"
    return street.matches(streetRegex.toRegex())
}

private fun isValidNumber(number: String): Boolean {
    val numberRegex = "^\\d+[a-zA-Z]*\$"
    return number.matches(numberRegex.toRegex())
}

private fun isValidCity(city: String): Boolean {
    val cityRegex = "^[A-Za-z]+(?:[\\s-][A-Za-z]+)*\$"
    return city.matches(cityRegex.toRegex())
}