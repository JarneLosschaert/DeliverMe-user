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
import be.howest.jarnelosschaert.deliverme.logic.Controller
import be.howest.jarnelosschaert.deliverme.ui.helpers.components.roundedBottomNav
import be.howest.jarnelosschaert.deliverme.ui.screens.*
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
}

@Composable
fun DeliverMeApp() {
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
            MainScreenNavigationConfigurations(
                modifier = Modifier.padding(innerPadding).padding(start = 15.dp, end = 8.dp),
                navController = navController,
                onNavigation = { pageClicked = it }
            )
        }
    )
}

@Composable
private fun MainScreenNavigationConfigurations(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    onNavigation: (String) -> Unit
) {
    val controller = Controller(navController)

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
            NotificationsScreen(modifier = modifier)
            onNavigation(BottomNavigationScreens.Notifications.route)
        }
        composable(BottomNavigationScreens.Settings.route) {
            SettingScreen(modifier = modifier,
                navigateTo = { controller.navigateTo(it) }
            )
            onNavigation(BottomNavigationScreens.Settings.route)
        }
        composable(OtherScreens.Deliver.route) {
            DeliverScreen(modifier = modifier,
                onGoBack = { controller.goBack() }
            )
            onNavigation(OtherScreens.Deliver.route)
        }
        composable(OtherScreens.Contacts.route) {
            ContactsScreen(modifier = modifier,
                onGoBack = { controller.goBack() }
            )
            onNavigation(OtherScreens.Contacts.route)
        }
        composable(OtherScreens.Profile.route) {
            ProfileScreen(modifier = modifier,
                onGoBack = { controller.goBack() }
            )
        }
        composable(OtherScreens.PackageDetails.route) {
            PackageDetailsScreen(modifier = modifier,
                onGoBack = { controller.goBack() }
            )
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
                icon = { Icon(screen.icon, contentDescription = null, tint = color, modifier = Modifier.size(36.dp)) },
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