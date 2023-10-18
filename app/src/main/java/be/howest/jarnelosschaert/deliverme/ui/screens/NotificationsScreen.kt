package be.howest.jarnelosschaert.deliverme.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import be.howest.jarnelosschaert.deliverme.ui.helpers.components.Content
import be.howest.jarnelosschaert.deliverme.ui.helpers.components.DateDetails
import be.howest.jarnelosschaert.deliverme.ui.helpers.components.SmallButton
import be.howest.jarnelosschaert.deliverme.ui.helpers.components.Title

@Composable
fun NotificationsScreen(
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxWidth()) {
        Column {
            Title(text = "Notifications")
            Notification()
        }
    }
}

@Composable
fun Notification() {
    Column(
        modifier = Modifier.border(1.dp, Color.Black).padding(10.dp),
    ) {
        Content(text = "Your package has been sent.")
        SmallButton(modifier = Modifier.padding(top = 10.dp), onClick = {})
        DateDetails(text = "12/12/2021")
    }
}