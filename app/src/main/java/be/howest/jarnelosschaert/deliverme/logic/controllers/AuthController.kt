package be.howest.jarnelosschaert.deliverme.logic.controllers

import androidx.navigation.NavController
import be.howest.jarnelosschaert.deliverme.ui.AuthorizeScreens

class AuthController(
    private val navController: NavController
) {
    private var isLoggedIn: Boolean = false

    fun login() {
        isLoggedIn = true
        navController.navigate(AuthorizeScreens.App.route)
    }

    fun logout() {
        isLoggedIn = false
        navController.navigate(AuthorizeScreens.Login.route)
    }

    fun signUp() {
        login()
    }

    fun deleteAccount() {
        logout()
    }

    fun isLoggedIn(): Boolean {
        return isLoggedIn
    }
}