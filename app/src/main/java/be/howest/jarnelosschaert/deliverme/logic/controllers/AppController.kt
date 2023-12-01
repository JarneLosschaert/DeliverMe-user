package be.howest.jarnelosschaert.deliverme.logic.controllers

import androidx.navigation.NavController
import be.howest.jarnelosschaert.deliverme.logic.AppUiState
import be.howest.jarnelosschaert.deliverme.ui.BottomNavigationScreens

class AppController(
    private val navController: NavController
) {
    private val uiState: AppUiState = AppUiState()

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