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
    object Contact : OtherScreens("contact")
    object Profile : OtherScreens("profile")
    object DeliveryDetails : OtherScreens("deliveryDetails")
    object Address : OtherScreens("address")
    object Map : OtherScreens("map")
}

@Composable
fun DeliverMeApp(authController: AuthController) {
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
                onNavigate = { pageClicked = it }
            )
        }
    )
}

@Composable
private fun AuthScreenNavigationConfigurations(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    authController: AuthController,
    onNavigate: (String) -> Unit
) {
    val controller = AppController(navController = navController, authController = authController)

    NavHost(navController, startDestination = BottomNavigationScreens.Home.route) {
        composable(BottomNavigationScreens.Home.route) {
            HomeScreen(modifier = modifier,
                activeDeliveries = controller.uiState.activeDeliveries,
                pastDeliveries = controller.uiState.pastDeliveries,
                onDeliveryClick = { controller.onDeliveryClicked(it) },
                navigateDeliver = { controller.navigateTo(OtherScreens.Deliver.route) },
                navigateContacts = { controller.navigateTo(OtherScreens.Contacts.route) },
            )
            onNavigate(BottomNavigationScreens.Home.route)
        }
        composable(BottomNavigationScreens.Notifications.route) {
            NotificationsScreen(modifier = modifier,
                showPackageDetails = { controller.navigateTo(OtherScreens.DeliveryDetails.route) }
            )
            onNavigate(BottomNavigationScreens.Notifications.route)
        }
        composable(BottomNavigationScreens.Settings.route) {
            SettingScreen(
                modifier = modifier,
                navigateTo = { controller.navigateTo(it) },
            )
            onNavigate(BottomNavigationScreens.Settings.route)
        }
        composable(OtherScreens.Deliver.route) {
            DeliverScreen(
                modifier = modifier,
                contacts = authController.uiState.customer.contacts,
                senderAddress = controller.uiState.senderAddress,
                receiver = controller.uiState.receiver,
                receiverAddress = controller.uiState.receiverAddress,
                packageSize = controller.uiState.packageSize,
                description = controller.uiState.description,
                onGoBack = { controller.goBack() },
                onSenderAddressChange = { controller.onSenderAddressChange() },
                onReceiverChange = { controller.onReceiverChange(it) },
                onReceiverAddressChange = { controller.onReceiverAddressChange() },
                onPackageSizeChange = { controller.uiState.packageSize = it },
                onDescriptionChange = { controller.uiState.description = it },
                createPackage = { controller.createPackage() },
            )
            onNavigate(OtherScreens.Deliver.route)
        }
        composable(OtherScreens.Contacts.route) {
            ContactsScreen(modifier = modifier,
                contacts = controller.getContacts(),
                query = controller.uiState.contactsQuery,
                onGoBack = { controller.goBack() },
                onQueryChange = { controller.uiState.contactsQuery = it },
                onContactClick = { controller.onContactClick(it) },
                addContact = { controller.addContact(it) },
            )
            onNavigate(OtherScreens.Contacts.route)
        }
        composable(OtherScreens.Contact.route) {
            ContactScreen(modifier = modifier,
                contact = controller.uiState.contact,
                onGoBack = { controller.goBack() },
                deleteContact = { controller.deleteContact() },
            )
            onNavigate(OtherScreens.Contact.route)
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
        composable(OtherScreens.DeliveryDetails.route) {
            DeliveryDetailsScreen(modifier = modifier,
                delivery = controller.uiState.delivery,
                onGoBack = { controller.goBack() },
                navigateMap = { controller.navigateTo(OtherScreens.Map.route) }
            )
            onNavigate(OtherScreens.DeliveryDetails.route)
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
            onNavigate(OtherScreens.Map.route)
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