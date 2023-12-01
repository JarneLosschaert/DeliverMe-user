package be.howest.jarnelosschaert.deliverme.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import be.howest.jarnelosschaert.deliverme.R
import be.howest.jarnelosschaert.deliverme.ui.helpers.components.Content
import be.howest.jarnelosschaert.deliverme.ui.helpers.components.GeneralTextField
import be.howest.jarnelosschaert.deliverme.ui.helpers.components.GeneralTextPopup
import be.howest.jarnelosschaert.deliverme.ui.helpers.components.Title
import be.howest.jarnelosschaert.deliverme.ui.helpers.functions.showPhoneNumber


@Composable
fun ContactsScreen(
    modifier: Modifier = Modifier,
    onGoBack: () -> Unit
) {
    val query = remember { mutableStateOf("") }

    var displayPopup by remember { mutableStateOf(false) }
    if (displayPopup) {
        GeneralTextPopup(
            title = "Add a contact",
            label = "Email",
            confirmButton = "Send",
            toastText = "Your request has been sent.",
            onDismiss = { displayPopup = false }
        )
    }
    Box(modifier = modifier.fillMaxWidth()) {
        Column {
            Title(text = "Contacts", onGoBack = onGoBack, withGoBack = true)
            SearchBar(
                query = query.value,
                onQueryChange = { query.value = it },
                displayContactPopup = { displayPopup = true }
            )
            LazyColumn(content =
            {
                item {
                    Contact()
                    Contact()
                    Contact()
                    Contact()
                    Contact()
                    Contact()
                    Contact()
                    Contact()
                    Contact()
                }
            })
        }
    }
}

@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    displayContactPopup: () -> Unit
) {
    Row() {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .width(280.dp)
                .padding(0.dp)
        ) {
            GeneralTextField(
                text = query,
                onValueChange = { onQueryChange(it) },
                placeholder = "Search contact"
            )
        }
        Image(
            painter = painterResource(id = R.drawable.add_contact),
            contentDescription = null,
            modifier = Modifier
                .padding(start = 0.dp)
                .align(Alignment.CenterVertically)
                .clickable(onClick = displayContactPopup)
        )
    }
    Spacer(modifier = Modifier.height(30.dp))
}

@Composable
fun Contact() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
            .padding(10.dp),
    ) {
        Content(text = "Glenn Callens", fontSize = 19)

        Info(text = "glenncallens@gmail.com", isEmail = true)
        Info(text = showPhoneNumber("0499 99 99 99"), isPhone = true)
        Info(text = "Kortrijksesteenweg 100, 9000 Gent")
    }
    Spacer(modifier = Modifier.height(10.dp))
}

@Composable
fun Info(
    text: String,
    isEmail: Boolean = false,
    isPhone: Boolean = false,
) {
    Row {
        var icon = Icons.Default.Home
        if (isEmail) icon = Icons.Default.Email
        if (isPhone) icon = Icons.Default.Phone
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colors.primary
        )
        Spacer(modifier = Modifier.width(10.dp))
        Content(text = text)
    }
}