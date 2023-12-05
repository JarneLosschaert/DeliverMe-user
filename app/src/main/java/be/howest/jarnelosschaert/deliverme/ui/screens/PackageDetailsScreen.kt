package be.howest.jarnelosschaert.deliverme.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import be.howest.jarnelosschaert.deliverme.logic.models.Delivery
import be.howest.jarnelosschaert.deliverme.ui.helpers.components.ContentLabel
import be.howest.jarnelosschaert.deliverme.ui.helpers.components.GeneralButton
import be.howest.jarnelosschaert.deliverme.ui.helpers.components.Title

@Composable
fun DeliveryDetailsScreen(
    modifier: Modifier = Modifier,
    delivery: Delivery,
    onGoBack: () -> Unit,
    navigateMap: () -> Unit
) {
    Box(modifier = modifier.fillMaxWidth()) {
        LazyColumn(content = {
            item {
                Title(text = "Delivery details", onGoBack = onGoBack, withGoBack = true)
                ContentLabel(label = "Sender", content = delivery.packageInfo.sender.person.name)
                ContentLabel(
                    label = "Address (sender)",
                    content = delivery.packageInfo.sender.homeAddress.toString
                )
                ContentLabel(label = "Receiver", content = delivery.packageInfo.receiver.person.name)
                ContentLabel(
                    label = "Address (receiver)",
                    content = delivery.packageInfo.receiver.homeAddress.toString
                )
                ContentLabel(label = "Date", content = "12/10/2022") // later change to delivery.date
                ContentLabel(label = "Departure", content = "13:20") // later change to delivery.departure
                ContentLabel(label = "Arrival", content = "13:41") // later change to delivery.arrival
                ContentLabel(label = "Description", content = delivery.packageInfo.description)
                GeneralButton(text = "See live location", onClick = navigateMap)
            }
        })
    }
}