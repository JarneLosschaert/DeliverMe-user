package be.howest.jarnelosschaert.deliverme.ui.screens

import android.location.Address
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import be.howest.jarnelosschaert.deliverme.R
import be.howest.jarnelosschaert.deliverme.ui.helpers.Content
import be.howest.jarnelosschaert.deliverme.ui.helpers.Title


@Composable
fun ContactsScreen(
    modifier: Modifier = Modifier
) {
    val query = remember { mutableStateOf("") }
    Box(modifier = modifier.fillMaxWidth()) {
        Column {
            Title(text = "Contacts")
            SearchBar(
                query = query.value,
                onQueryChange = { query.value = it },
            )
            Contact()
        }
    }
}

@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
) {
    Row() {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .width(280.dp)
                .border(1.dp, Color.Black)
                .padding(10.dp)
        ) {
            BasicTextField(
                value = query,
                onValueChange = { onQueryChange(it) },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        onQueryChange(query)
                    }
                ),
                textStyle = TextStyle(fontSize = 17.sp),
                modifier = Modifier.weight(1f)
            )
            Icon(imageVector = Icons.Default.Search, contentDescription = null)
        }
        Image(
            painter = painterResource(id = R.drawable.add_contact),
            contentDescription = null,
            modifier = Modifier
                .padding(start = 10.dp)
                .align(Alignment.CenterVertically)
        )
    }
    Spacer(modifier = Modifier.height(30.dp))
}

@Composable
fun Contact() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.Black)
            .padding(10.dp),
    ) {
        Content(text = "Glenn Calles", fontSize = 20)
        Address(address = "Kortrijksesteenweg 100, 9000 Gent")
        Address(address = "LageSteenweg 33, 9000 Gent", isExtra = true)
    }
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