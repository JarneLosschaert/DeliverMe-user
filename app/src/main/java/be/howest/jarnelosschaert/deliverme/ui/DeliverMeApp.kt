package be.howest.jarnelosschaert.deliverme.ui

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import be.howest.jarnelosschaert.deliverme.ui.helpers.roundedBottomNav
import be.howest.jarnelosschaert.deliverme.ui.screens.*

sealed class BottomNavigationScreens(val route: String, val icon: ImageVector) {
    object Home : BottomNavigationScreens("home", Icons.Filled.Home)
    object Notifications : BottomNavigationScreens("notifications", Icons.Filled.Notifications)
    object Settings : BottomNavigationScreens("settings", Icons.Filled.Settings)
}

sealed class OtherScreens(val route: String) {
    object Deliver : OtherScreens("deliver")
    object Contacts : OtherScreens("contacts")
}

//val uiState = UiState()

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
    NavHost(navController, startDestination = BottomNavigationScreens.Home.route) {
        composable(BottomNavigationScreens.Home.route) {
            HomeScreen(modifier = modifier,
                navigateDeliver = { navController.navigate(OtherScreens.Deliver.route) },
                navigateContacts = { navController.navigate(OtherScreens.Contacts.route) }
            )
            onNavigation(BottomNavigationScreens.Home.route)
        }
        composable(BottomNavigationScreens.Notifications.route) {
            NotificationsScreen(modifier = modifier)
            onNavigation(BottomNavigationScreens.Notifications.route)
        }
        composable(BottomNavigationScreens.Settings.route) {
            SettingScreen(modifier = modifier)
            onNavigation(BottomNavigationScreens.Settings.route)
        }
        composable(OtherScreens.Deliver.route) {
            DeliverScreen(modifier = modifier)
            onNavigation(OtherScreens.Deliver.route)
        }
        composable(OtherScreens.Contacts.route) {
            ContactsScreen(modifier = modifier)
            onNavigation(OtherScreens.Contacts.route)
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