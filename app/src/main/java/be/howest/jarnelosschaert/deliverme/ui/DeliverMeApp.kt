package be.howest.jarnelosschaert.deliverme.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import be.howest.jarnelosschaert.deliverme.logic.controllers.AppController
import be.howest.jarnelosschaert.deliverme.logic.controllers.AuthController
import be.howest.jarnelosschaert.deliverme.ui.helpers.components.roundedBottomNav
import be.howest.jarnelosschaert.deliverme.ui.screens.*
import be.howest.jarnelosschaert.deliverme.ui.screens.settingPages.AddressScreen
import be.howest.jarnelosschaert.deliverme.ui.screens.settingPages.ProfileScreen

sealed class BottomNavigationScreens(val route: String, val icon: ImageVector) {
    object Home : BottomNavigationScreens("home", Icons.Filled.Home)
    object Notifications : BottomNavigationScreens("notifications", Icons.Filled.Notifications)
    object Settings : BottomNavigationScreens("settings", Icons.Filled.Settings)
}

sealed class OtherScreens(val route: String) {
    object Deliver : OtherScreens("deliver")
    object Contacts : OtherScreens("contacts")
    object Profile : OtherScreens("profile")
    object PackageDetails : OtherScreens("packageDetails")
    object Address : OtherScreens("address")
    object Map : OtherScreens("map")
}

@Composable
fun DeliverMeApp(authController: AuthController) {
    //HandleNotifications()

    val navController = rememberNavController()
    val bottomNavigationItems = listOf(
        BottomNavigationScreens.Home,
        BottomNavigationScreens.Notifications,
        BottomNavigationScreens.Settings,
    )

    var pageClicked by remember { mutableStateOf(BottomNavigationScreens.Home.route) }

    Scaffold(
        bottomBar = {
            DeliverMeBottomNavigation(navController, bottomNavigationItems, pageClicked)
        },
        content = { innerPadding ->
            AuthScreenNavigationConfigurations(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(start = 15.dp, end = 8.dp),
                navController = navController,
                authController = authController,
                onNavigation = { pageClicked = it }
            )
        }
    )
}

@Composable
private fun AuthScreenNavigationConfigurations(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    authController: AuthController,
    onNavigation: (String) -> Unit
) {
    val controller = AppController(navController = navController, authController = authController)

    NavHost(navController, startDestination = BottomNavigationScreens.Home.route) {
        composable(BottomNavigationScreens.Home.route) {
            HomeScreen(modifier = modifier,
                navigateDeliver = { controller.navigateTo(OtherScreens.Deliver.route) },
                navigateContacts = { controller.navigateTo(OtherScreens.Contacts.route) },
                showPackageDetails = { controller.navigateTo(OtherScreens.PackageDetails.route) }
            )
            onNavigation(BottomNavigationScreens.Home.route)
        }
        composable(BottomNavigationScreens.Notifications.route) {
            NotificationsScreen(modifier = modifier,
                showPackageDetails = { controller.navigateTo(OtherScreens.PackageDetails.route) }
            )
            onNavigation(BottomNavigationScreens.Notifications.route)
        }
        composable(BottomNavigationScreens.Settings.route) {
            SettingScreen(
                modifier = modifier,
                navigateTo = { controller.navigateTo(it) },
            )
            onNavigation(BottomNavigationScreens.Settings.route)
        }
        composable(OtherScreens.Deliver.route) {
            DeliverScreen(
                modifier = modifier,
                contacts = controller.uiState.contacts,
                senderAddress = controller.uiState.senderAddress,
                receiver = controller.uiState.receiver,
                receiverAddress = controller.uiState.receiverAddress,
                packageSize = controller.uiState.packageSize,
                onPackageSizeChange = { controller.uiState.packageSize = it },
                description = controller.uiState.description,
                onGoBack = { controller.goBack() },
                onSenderAddressChange = { controller.onSenderAddressChange() },
                onReceiverChange = { controller.onReceiverChange(it) },
                onReceiverAddressChange = { controller.onReceiverAddressChange() },
                onDescriptionChange = { controller.uiState.description = it },
            )
            onNavigation(OtherScreens.Deliver.route)
        }
        composable(OtherScreens.Contacts.route) {
            ContactsScreen(modifier = modifier,
                contacts = controller.getContacts(),
                query = controller.uiState.contactsQuery,
                onGoBack = { controller.goBack() },
                onQueryChange = { controller.uiState.contactsQuery = it },
            )
            onNavigation(OtherScreens.Contacts.route)
        }
        composable(OtherScreens.Profile.route) {
            ProfileScreen(modifier = modifier,
                customer = authController.uiState.customer,
                onGoBack = { controller.goBack() },
                navigateAddress = { controller.navigateTo(OtherScreens.Address.route) },
                logout = { authController.logout() },
                deleteAccount = { authController.deleteAccount() }
            )
        }
        composable(OtherScreens.PackageDetails.route) {
            PackageDetailsScreen(modifier = modifier,
                onGoBack = { controller.goBack() },
                navigateMap = { controller.navigateTo(OtherScreens.Map.route) }
            )
            onNavigation(OtherScreens.PackageDetails.route)
        }
        composable(OtherScreens.Address.route) {
            AddressScreen(modifier = modifier,
                subtitle = controller.uiState.addressScreenStatus.subtitle,
                errors = controller.uiState.addressErrors,
                onGoBack = { controller.goBack() },
                onConfirmAddress = { controller.onConfirmAddress(it) },
            )
        }
        composable(OtherScreens.Map.route) {
            MapScreen(
                onGoBack = { controller.goBack() }
            )
            onNavigation(OtherScreens.Map.route)
        }
    }
}

@Composable
fun DeliverMeBottomNavigation(
    navController: NavHostController,
    items: List<BottomNavigationScreens>,
    pageClicked: String
) {

    BottomNavigation(
        backgroundColor = MaterialTheme.colors.secondary,
        contentColor = MaterialTheme.colors.onBackground,
        modifier = Modifier.clip(roundedBottomNav()),
    ) {
        val currentRoute = currentRoute(navController)
        items.forEach { screen ->
            var color = Color.White
            if (pageClicked == screen.route) {
                color = MaterialTheme.colors.primary
            }
            BottomNavigationItem(
                icon = {
                    Icon(
                        screen.icon,
                        contentDescription = null,
                        tint = color,
                        modifier = Modifier.size(36.dp)
                    )
                },
                selected = currentRoute == screen.route,
                onClick = {
                    if (currentRoute != screen.route) {
                        navController.navigate(screen.route)
                    }
                }
            )
        }
    }
}

@Composable
private fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.arguments?.getString("")
}