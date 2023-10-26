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

sealed class AuthorizeScreens(val route: String) {
    object Login : AuthorizeScreens("login")
    object SignUp : AuthorizeScreens("signUp")
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
                navigateToSignUp = { navController.navigate(AuthorizeScreens.SignUp.route) },
                login = { controller.login() }
            )
        }
        composable(AuthorizeScreens.SignUp.route) {
            SignUpScreen(
                modifier = modifier,
                navigateToLogin = { navController.navigate(AuthorizeScreens.Login.route) },
                signUp = { controller.signUp() }
            )
        }
        composable(AuthorizeScreens.App.route) {
            DeliverMeApp(authController = controller)
        }
    }
}
