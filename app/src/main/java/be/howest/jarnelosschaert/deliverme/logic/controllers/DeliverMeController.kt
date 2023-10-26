package be.howest.jarnelosschaert.deliverme.logic.controllers

import androidx.navigation.NavController
import be.howest.jarnelosschaert.deliverme.logic.UiState
import be.howest.jarnelosschaert.deliverme.ui.BottomNavigationScreens

class DeliverMeController(private val navController: NavController) {
    private val uiState: UiState = UiState()

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