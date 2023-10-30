package be.howest.jarnelosschaert.deliverme.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import be.howest.jarnelosschaert.deliverme.R
import be.howest.jarnelosschaert.deliverme.ui.helpers.GeneralTextPopup
import be.howest.jarnelosschaert.deliverme.ui.helpers.components.Content
import be.howest.jarnelosschaert.deliverme.ui.helpers.components.GeneralTextField
import be.howest.jarnelosschaert.deliverme.ui.helpers.components.Title


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
            GeneralTextField(text = query, onValueChange = { onQueryChange(it) }, placeholder = "Search contact")
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
        Address(address = "Kortrijksesteenweg 100, 9000 Gent")
        Address(address = "LageSteenweg 33, 9000 Gent", isExtra = true)
    }
    Spacer(modifier = Modifier.height(10.dp))
}

@Composable
fun Address(
    address: String,
    isExtra: Boolean = false
) {
    Row {
        if (isExtra) {
            Icon(imageVector = Icons.Default.Add, contentDescription = null)
        } else {
            Icon(imageVector = Icons.Default.Home, contentDescription = null)
        }
        Spacer(modifier = Modifier.width(10.dp))
        Content(text = address)
    }
}