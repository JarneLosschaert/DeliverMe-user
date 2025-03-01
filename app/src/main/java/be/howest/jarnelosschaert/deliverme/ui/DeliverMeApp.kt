package be.howest.jarnelosschaert.deliverme.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import be.howest.jarnelosschaert.deliverme.logic.controllers.AppController
import be.howest.jarnelosschaert.deliverme.logic.controllers.AuthController
import be.howest.jarnelosschaert.deliverme.logic.helpers.notifications.Location
import be.howest.jarnelosschaert.deliverme.logic.helpers.notifications.Notifier
import be.howest.jarnelosschaert.deliverme.ui.helpers.components.roundedBottomNav
import be.howest.jarnelosschaert.deliverme.ui.helpers.functions.HandleNotificationPermissions
import be.howest.jarnelosschaert.deliverme.ui.screens.*
import be.howest.jarnelosschaert.deliverme.ui.screens.settingScreens.AddressScreen
import be.howest.jarnelosschaert.deliverme.ui.screens.settingScreens.ProfileScreen

sealed class BottomNavigationScreens(val route: String, val icon: ImageVector) {
    object Home : BottomNavigationScreens("home", Icons.Filled.Home)
    object Notifications : BottomNavigationScreens("notifications", Icons.Filled.Notifications)
    object Settings : BottomNavigationScreens("settings", Icons.Filled.Settings)
}

sealed class OtherScreens(val route: String) {
    object Deliver : OtherScreens("deliver")
    object Pay : OtherScreens("pay")
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
    val controller = AppController(navController = navController, authController = authController)
    val bottomNavigationItems = listOf(
        BottomNavigationScreens.Home,
        BottomNavigationScreens.Notifications,
        BottomNavigationScreens.Settings,
    )

    var pageClicked by remember { mutableStateOf(BottomNavigationScreens.Home.route) }

    Notifier(
        context = navController.context,
        email = authController.uiState.customer.person.email,
        onLocationReceived = { delivery, location ->
            controller.onDriverLocationUpdate(
                delivery, Location(location.latitude, location.longitude)
            )
        }
    )
    HandleNotificationPermissions()

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
                controller = controller,
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
    controller: AppController,
    onNavigate: (String) -> Unit
) {
    NavHost(navController, startDestination = BottomNavigationScreens.Home.route) {
        composable(BottomNavigationScreens.Home.route) {
            HomeScreen(
                modifier = modifier,
                paidDeliveries = controller.uiState.paidDeliveries,
                assignedDeliveries = controller.uiState.assignedDeliveries,
                transitDeliveries = controller.uiState.transitDeliveries,
                deliveredDeliveries = controller.uiState.deliveredDeliveries,
                refreshing = controller.uiState.refreshing,
                loggedInCustomer = authController.uiState.customer,
                onRefreshDeliveries = { controller.loadDeliveries(refreshing = true) },
                onDeliveryClick = { controller.onDeliveryClicked(it) },
                navigateDeliver = { controller.navigateTo(OtherScreens.Deliver.route) },
                navigateContacts = { controller.navigateTo(OtherScreens.Contacts.route) },
            )
            onNavigate(BottomNavigationScreens.Home.route)
        }
        composable(BottomNavigationScreens.Notifications.route) {
            NotificationsScreen(
                modifier = modifier,
                notifications = controller.notificationsManager.getNotifications(),
                showPackageDetails = { controller.onNotificationTap(it) },
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
                packageErrors = controller.uiState.packageErrors,
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
        composable(OtherScreens.Pay.route) {
            PayScreen(
                modifier = modifier,
                appPackage = controller.uiState.appPackage,
                context = LocalContext.current,
                payResponse = controller.uiState.payResponse,
                onGoBack = { controller.goBack() },
                onPay = { controller.onPay(it) },
            )
            onNavigate(OtherScreens.Pay.route)
        }
        composable(OtherScreens.Contacts.route) {
            ContactsScreen(
                modifier = modifier,
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
            ContactScreen(
                modifier = modifier,
                contact = controller.uiState.selectedContact,
                deliveries = controller.getContactDeliveries(),
                loggedInCustomer = authController.uiState.customer,
                onDeliveryClick = { controller.onDeliveryClicked(it) },
                onGoBack = { controller.goBack() },
                deleteContact = { controller.deleteContact() },
            )
            onNavigate(OtherScreens.Contact.route)
        }
        composable(OtherScreens.Profile.route) {
            ProfileScreen(
                modifier = modifier,
                customer = authController.uiState.customer,
                onGoBack = { controller.goBack() },
                logout = { authController.logout() },
                deleteAccount = { authController.deleteCustomer() },
                changeUserName = { authController.updateCustomer(name = it) },
                changeEmail = { authController.updateCustomer(email = it) },
                changePhone = { authController.updateCustomer(phone = it) },
                changeAddress = { controller.changeAddress() },
                changePassword = { authController.changePassword(it) },
            )
        }
        composable(OtherScreens.DeliveryDetails.route) {
            DeliveryDetailsScreen(modifier = modifier,
                delivery = controller.uiState.selectedDelivery,
                onGoBack = { controller.goBack() },
                navigateMap = { controller.navigateTo(OtherScreens.Map.route) }
            )
            onNavigate(OtherScreens.DeliveryDetails.route)
        }
        composable(OtherScreens.Address.route) {
            AddressScreen(
                modifier = modifier,
                subtitle = controller.uiState.addressScreenStatus.subtitle,
                errors = controller.uiState.addressErrors,
                onGoBack = { controller.goBack() },
                onConfirmAddress = { controller.onConfirmAddress(it) },
            )
        }
        composable(OtherScreens.Map.route) {
            MapScreen(
                senderAddress = controller.uiState.selectedDelivery.`package`.senderAddress,
                receiverAddress = controller.uiState.selectedDelivery.`package`.receiverAddress,
                locationDriver = controller.driversLocationManager.getDriverLocation(controller.uiState.selectedDelivery.id),
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