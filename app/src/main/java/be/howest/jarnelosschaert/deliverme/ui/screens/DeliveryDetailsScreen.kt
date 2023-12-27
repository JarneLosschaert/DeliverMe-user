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
                ContentLabel(label = "Sender", content = delivery.packageInfo.sender.person.name)
                ContentLabel(
                    label = "Address (sender)",
                    content = showAddress(delivery.packageInfo.sender.homeAddress)
                )
                ContentLabel(
                    label = "Receiver",
                    content = delivery.packageInfo.receiver.person.name
                )
                ContentLabel(
                    label = "Address (receiver)",
                    content = showAddress(delivery.packageInfo.receiver.homeAddress)
                )
                ContentLabel(label = "State", content = delivery.state.value)
                ContentLabel(label = "Description", content = delivery.packageInfo.description)
                ContentLabel(
                    label = "Package size",
                    content = delivery.packageInfo.packageSize.value
                )
                ContentLabel(label = "Fee", content = "€ ${delivery.packageInfo.fee}")
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
                if (delivery.state != DeliveryState.DELIVERED) {
                    GeneralButton(text = "See live location", onClick = navigateMap)
                }
            }
        })
    }
}