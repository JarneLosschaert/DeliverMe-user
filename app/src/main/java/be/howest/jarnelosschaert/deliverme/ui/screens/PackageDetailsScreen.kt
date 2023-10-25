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
    modifier: Modifier = Modifier,
    onGoBack: () -> Unit
) {
    Box(modifier = modifier.fillMaxWidth()) {
        Column {
            Title(text = "Package details", onGoBack = onGoBack, withGoBack = true)

            PackageDetail(label = "Sender", content = "Daan Hautekiet")
            PackageDetail(label = "Address", content = "Kortrijksestraat 12, 8500 Kortrijk")
            PackageDetail(label = "Receiver", content = "Glenn Callens")
            PackageDetail(label = "Address", content = "Kortrijksestraat 12, 8500 Kortrijk")
            PackageDetail(label = "Departure", content = "13:20")
            PackageDetail(label = "Expected arrival", content = "13:40")
            PackageDetail(label = "Arrival", content = "13:41")
            PackageDetail(label = "Description", content = "A small package")

            SmallButton(text = "See live location", onClick = {})
        }
    }
}

@Composable
fun PackageDetail(label: String, content: String) {
    Label(text = label)
    Content(text = content)
    Spacer(modifier = Modifier.height(10.dp))
}