package be.howest.jarnelosschaert.deliverme.ui.screens.settingPages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import be.howest.jarnelosschaert.deliverme.ui.helpers.components.Title

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxWidth()) {
        Title("Profile")
    }
}