package be.howest.jarnelosschaert.deliverme.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import be.howest.jarnelosschaert.deliverme.logic.controllers.AuthController
import be.howest.jarnelosschaert.deliverme.ui.screens.authScreens.LoginScreen
import be.howest.jarnelosschaert.deliverme.ui.screens.authScreens.SignUpScreen
import be.howest.jarnelosschaert.deliverme.ui.screens.settingPages.AddressScreen

sealed class AuthorizeScreens(val route: String) {
    object Login : AuthorizeScreens("login")
    object SignUp : AuthorizeScreens("signUp")
    object Address : AuthorizeScreens("address")
    object App : AuthorizeScreens("app")
}

@Composable
fun Authorize() {
    val navController = rememberNavController()
    val controller = AuthController(navController = navController)

    if (controller.isLoggedIn()) {
        navController.navigate(AuthorizeScreens.App.route)
    } else {
        AuthorizeNavigation(controller = controller, navController = navController)
    }
}

@Composable
fun AuthorizeNavigation(controller: AuthController, navController: NavHostController) {
    Box(modifier = Modifier.fillMaxWidth()) {
        AuthScreenNavigationConfigurations(navController = navController, controller = controller)
    }
}

@Composable
private fun AuthScreenNavigationConfigurations(
    navController: NavHostController,
    controller: AuthController
) {
    val modifier = Modifier.padding(start = 15.dp, end = 8.dp)
    NavHost(navController, startDestination = AuthorizeScreens.Login.route) {
        composable(AuthorizeScreens.Login.route) {
            LoginScreen(
                modifier = modifier,
                errors = controller.uiState.loginErrors,
                navigateToSignUp = { navController.navigate(AuthorizeScreens.SignUp.route) },
                login = { email, password -> controller.login(email, password) }
            )
        }
        composable(AuthorizeScreens.SignUp.route) {
            SignUpScreen(
                modifier = modifier,
                errors = controller.uiState.signUpErrors,
                navigateToLogin = { navController.navigate(AuthorizeScreens.Login.route) },
                signUp = { controller.checkSignUp(it) },
            )
        }
        composable(AuthorizeScreens.Address.route) {
            AddressScreen(
                modifier = Modifier.padding(start = 15.dp, end = 8.dp),
                subtitle = "Please enter your home address",
                errors = controller.uiState.addressErrors,
                onGoBack = { navController.popBackStack() },
                onConfirmAddress = { controller.signUp(it) }
            )
        }
        composable(AuthorizeScreens.App.route) {
            DeliverMeApp(authController = controller)
        }
    }
}
