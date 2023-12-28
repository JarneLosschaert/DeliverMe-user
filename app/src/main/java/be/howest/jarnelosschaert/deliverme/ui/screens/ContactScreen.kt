package be.howest.jarnelosschaert.deliverme.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import be.howest.jarnelosschaert.deliverme.logic.models.Customer
import be.howest.jarnelosschaert.deliverme.logic.models.Delivery
import be.howest.jarnelosschaert.deliverme.ui.helpers.components.*
import be.howest.jarnelosschaert.deliverme.ui.helpers.functions.showAddress
import be.howest.jarnelosschaert.deliverme.ui.helpers.functions.showPhoneNumber

@Composable
fun ContactScreen(
    modifier: Modifier = Modifier,
    contact: Customer,
    deliveries: List<Delivery>,
    loggedInCustomer: Customer,
    onDeliveryClick: (Delivery) -> Unit,
    onGoBack: () -> Unit,
    deleteContact: () -> Unit
) {
    Box(modifier = modifier.fillMaxWidth()) {
        Column {
            Title(text = "Contact", onGoBack = onGoBack, withGoBack = true)
            LazyColumn(content = {
                item {
                    SubTitle(text = "Contact details")
                    ContentLabel(label = "Username", content = contact.person.name)
                    ContentLabel(
                        label = "Phone number",
                        content = showPhoneNumber(contact.person.phone)
                    )
                    ContentLabel(label = "Email", content = contact.person.email)
                    ContentLabel(label = "Home address", content = showAddress(contact.homeAddress))
                    GeneralButton(
                        text = "Delete contact",
                        onClick = deleteContact
                    )
                    SubTitle(text = "History with ${contact.person.name}")
                    for (delivery in deliveries) {
                        Delivery(
                            delivery = delivery,
                            onDeliveryClick = onDeliveryClick,
                            loggedInCustomer = loggedInCustomer
                        )
                    }
                    if (deliveries.isEmpty()) {
                        Content(text = "No history with ${contact.person.name} yet")
                    }
                }
            })
        }
    }
}