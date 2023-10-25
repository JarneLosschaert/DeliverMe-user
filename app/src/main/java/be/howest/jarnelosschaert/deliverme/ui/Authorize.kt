package be.howest.jarnelosschaert.deliverme.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import be.howest.jarnelosschaert.deliverme.ui.screens.authScreens.LoginScreen



@Composable
fun Authorize() {
    val loggedIn = false

    if (loggedIn) {
        DeliverMeApp()
    } else {
        AuthorizeNavigation()
    }
}

@Composable
fun AuthorizeNavigation() {
    val navController = rememberNavController()

    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(start = 15.dp, end = 8.dp)) {
        LoginScreen()
    }
}