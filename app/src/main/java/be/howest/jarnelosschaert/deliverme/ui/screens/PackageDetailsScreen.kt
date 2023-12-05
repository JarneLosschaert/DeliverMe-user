package be.howest.jarnelosschaert.deliverme.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import be.howest.jarnelosschaert.deliverme.ui.helpers.components.ContentLabel
import be.howest.jarnelosschaert.deliverme.ui.helpers.components.GeneralButton
import be.howest.jarnelosschaert.deliverme.ui.helpers.components.Title

@Composable
fun PackageDetailsScreen(
    modifier: Modifier = Modifier,
    onGoBack: () -> Unit,
    navigateMap: () -> Unit
) {
    Box(modifier = modifier.fillMaxWidth()) {
        LazyColumn(content = {
            item {
                Title(text = "Package details", onGoBack = onGoBack, withGoBack = true)

                ContentLabel(label = "Sender", content = "Daan Hautekiet")
                ContentLabel(
                    label = "Address (sender)",
                    content = "Kortrijksestraat 12, 8500 Kortrijk"
                )
                ContentLabel(label = "Receiver", content = "Glenn Callens")
                ContentLabel(
                    label = "Address (receiver)",
                    content = "Kortrijksestraat 12, 8500 Kortrijk"
                )
                ContentLabel(label = "Date", content = "12/10/2022")
                ContentLabel(label = "Departure", content = "13:20")
                ContentLabel(label = "Expected arrival", content = "13:40")
                ContentLabel(label = "Arrival", content = "13:41")
                ContentLabel(label = "Description", content = "A small package")

                GeneralButton(text = "See live location", onClick = navigateMap)
            }
        })
    }
}