package be.howest.jarnelosschaert.deliverme.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import be.howest.jarnelosschaert.deliverme.logic.helpers.notifications.Notification
import be.howest.jarnelosschaert.deliverme.logic.models.Delivery
import be.howest.jarnelosschaert.deliverme.ui.helpers.components.Content
import be.howest.jarnelosschaert.deliverme.ui.helpers.components.DateDetails
import be.howest.jarnelosschaert.deliverme.ui.helpers.components.GeneralButton
import be.howest.jarnelosschaert.deliverme.ui.helpers.components.Title
import be.howest.jarnelosschaert.deliverme.ui.helpers.functions.showDate

@Composable
fun NotificationsScreen(
    modifier: Modifier = Modifier,
    notifications: List<Notification>,
    showPackageDetails: (Delivery) -> Unit
) {
    Box(modifier = modifier.fillMaxWidth()) {
        Column {
            Title(text = "Notifications")
            LazyColumn(content = {
                item {
                    for (notification in notifications) {
                        Notification(
                            notification = notification,
                            showPackageDetails = showPackageDetails
                        )
                    }
                    if (notifications.isEmpty()) {
                        Content(text = "No notifications yet")
                    }
                }
            })
        }
    }
}

@Composable
fun Notification(
    notification: Notification,
    showPackageDetails: (Delivery) -> Unit
) {
    Column(
        modifier = Modifier
            .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
            .padding(10.dp),
    ) {
        Content(text = notification.message)
        GeneralButton(
            modifier = Modifier.padding(top = 10.dp),
            onClick = { showPackageDetails(notification.delivery) }
        )
        DateDetails(text = showDate(notification.delivery.dateTimeDeparted))
    }
    Spacer(modifier = Modifier.height(10.dp))
}