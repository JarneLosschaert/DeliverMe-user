package be.howest.jarnelosschaert.deliverme.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import be.howest.jarnelosschaert.deliverme.logic.models.Customer
import be.howest.jarnelosschaert.deliverme.ui.helpers.components.ContentLabel
import be.howest.jarnelosschaert.deliverme.ui.helpers.components.Title
import be.howest.jarnelosschaert.deliverme.ui.helpers.functions.showAddress
import be.howest.jarnelosschaert.deliverme.ui.helpers.functions.showPhoneNumber

@Composable
fun ContactScreen(
    modifier: Modifier = Modifier,
    contact: Customer,
    //deliveries: List<String>,
    onGoBack: () -> Unit
) {
    Box(modifier = modifier.fillMaxWidth()) {
        Column {
            Title(text = "Contact", onGoBack = onGoBack, withGoBack = true)
            LazyColumn(content = {
                item {
                    ContentLabel(label = "Username", content = contact.person.name)
                    ContentLabel(
                        label = "Phone number",
                        content = showPhoneNumber(contact.person.phone)
                    )
                    ContentLabel(label = "Email", content = contact.person.email)
                    ContentLabel(label = "Home address", content = contact.homeAddress.toString)
                }
            })
        }
    }
}