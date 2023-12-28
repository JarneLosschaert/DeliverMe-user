package be.howest.jarnelosschaert.deliverme.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import be.howest.jarnelosschaert.deliverme.logic.models.Delivery
import be.howest.jarnelosschaert.deliverme.logic.models.DeliveryState
import be.howest.jarnelosschaert.deliverme.ui.helpers.components.ContentLabel
import be.howest.jarnelosschaert.deliverme.ui.helpers.components.GeneralButton
import be.howest.jarnelosschaert.deliverme.ui.helpers.components.Title
import be.howest.jarnelosschaert.deliverme.ui.helpers.functions.*

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
                ContentLabel(label = "Sender", content = delivery.`package`.sender.person.name)
                ContentLabel(
                    label = "Address (sender)",
                    content = showAddress(delivery.`package`.sender.homeAddress)
                )
                ContentLabel(
                    label = "Receiver",
                    content = delivery.`package`.receiver.person.name
                )
                ContentLabel(
                    label = "Address (receiver)",
                    content = showAddress(delivery.`package`.receiver.homeAddress)
                )
                ContentLabel(label = "State", content = delivery.state.toString()) // value
                ContentLabel(label = "Description", content = delivery.`package`.description)
                ContentLabel(
                    label = "Package size",
                    content = delivery.`package`.packageSize.value
                )
                ContentLabel(label = "Fee", content = "€ ${delivery.`package`.fee}")
                if (delivery.dateTimeDeparted != "") {
                    ContentLabel(
                        label = "Date",
                        content = showDate(delivery.dateTimeDeparted)
                    )
                    ContentLabel(
                        label = "Departure",
                        content = showTime(delivery.dateTimeDeparted)
                    )
                }
                if (delivery.dateTimeArrived != "") {
                    ContentLabel(
                        label = "Arrival",
                        content = showTime(delivery.dateTimeArrived)
                    )
                }
                if (delivery.state != DeliveryState.delivered) {
                    GeneralButton(text = "See live location", onClick = navigateMap)
                }
            }
        })
    }
}