package be.howest.jarnelosschaert.deliverme.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import be.howest.jarnelosschaert.deliverme.ui.helpers.components.Content
import be.howest.jarnelosschaert.deliverme.ui.helpers.components.Label
import be.howest.jarnelosschaert.deliverme.ui.helpers.components.SmallButton
import be.howest.jarnelosschaert.deliverme.ui.helpers.components.Title

@Composable
fun PackageDetailsScreen(
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxWidth()) {
        Column {
            Title(text = "Package details")
            Label(text = "Sender")
            Content(text = "Daan Hautekiet")
            Spacer(modifier = Modifier.height(10.dp))
            Label(text = "Address")
            Content(text = "Kortrijksestraat 12, 8500 Kortrijk")
            Spacer(modifier = Modifier.height(10.dp))
            Label(text = "Receiver")
            Content(text = "Glenn Callens")
            Spacer(modifier = Modifier.height(10.dp))
            Label(text = "Address")
            Content(text = "Kortrijksestraat 12, 8500 Kortrijk")
            Spacer(modifier = Modifier.height(10.dp))
            Label(text = "Departure")
            Content(text = "13:20")
            Spacer(modifier = Modifier.height(10.dp))
            Label(text = "Expected arrival")
            Content(text = "13:40")
            Spacer(modifier = Modifier.height(10.dp))
            Label(text = "Arrival")
            Content(text = "13:41")
            Spacer(modifier = Modifier.height(10.dp))
            Label(text = "Description")
            Content(text = "A small package")
            Spacer(modifier = Modifier.height(10.dp))
            SmallButton(text = "See live location", onClick = {})
        }
    }
}