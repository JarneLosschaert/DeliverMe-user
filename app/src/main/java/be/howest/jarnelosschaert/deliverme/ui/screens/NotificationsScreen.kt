package be.howest.jarnelosschaert.deliverme.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import be.howest.jarnelosschaert.deliverme.ui.helpers.components.Content
import be.howest.jarnelosschaert.deliverme.ui.helpers.components.DateDetails
import be.howest.jarnelosschaert.deliverme.ui.helpers.components.GeneralButton
import be.howest.jarnelosschaert.deliverme.ui.helpers.components.Title

@Composable
fun NotificationsScreen(
    modifier: Modifier = Modifier,
    showPackageDetails: () -> Unit
) {
    Box(modifier = modifier.fillMaxWidth()) {
        Column {
            Title(text = "Notifications")
            LazyColumn(content = {
                item {
                    Notification(showPackageDetails = showPackageDetails)
                    Notification(showPackageDetails = showPackageDetails)
                    Notification(showPackageDetails = showPackageDetails)
                    Notification(showPackageDetails = showPackageDetails)
                    Notification(showPackageDetails = showPackageDetails)
                    Notification(showPackageDetails = showPackageDetails)
                    Notification(showPackageDetails = showPackageDetails)
                    Notification(showPackageDetails = showPackageDetails)
                    Notification(showPackageDetails = showPackageDetails)
                    Notification(showPackageDetails = showPackageDetails)
                }
            })
        }
    }
}

@Composable
fun Notification(
    showPackageDetails: () -> Unit
) {
    Column(
        modifier = Modifier
            .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
            .padding(10.dp),
    ) {
        Content(text = "Your package has been sent.")
        GeneralButton(modifier = Modifier.padding(top = 10.dp), onClick = showPackageDetails)
        DateDetails(text = "12/12/2021")
    }
    Spacer(modifier = Modifier.height(10.dp))
}