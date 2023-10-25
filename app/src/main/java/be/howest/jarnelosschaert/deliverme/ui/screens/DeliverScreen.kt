package be.howest.jarnelosschaert.deliverme.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import be.howest.jarnelosschaert.deliverme.ui.helpers.components.Content
import be.howest.jarnelosschaert.deliverme.ui.helpers.components.Label
import be.howest.jarnelosschaert.deliverme.ui.helpers.components.SmallButton
import be.howest.jarnelosschaert.deliverme.ui.helpers.components.Title

@Composable
fun DeliverScreen(
    modifier: Modifier = Modifier,
    onGoBack: () -> Unit
) {
    Box(modifier = modifier.fillMaxWidth()) {
        Column {
            Title(onGoBack = onGoBack, withGoBack = true)
            DropDownLabel(
                label = "From",
                options = listOf("Home", "Work", "Other"),
                onOptionSelected = {}
            )
            DropDownLabel(
                label = "Receiver",
                options = listOf("Home", "Work", "Other"),
                onOptionSelected = {}
            )
            DropDownLabel(
                label = "Address",
                options = listOf("Small", "Medium", "Large"),
                onOptionSelected = {}
            )
            TextFieldLabel(label = "Description", input = "")
            SmallButton(modifier = Modifier.align(Alignment.CenterHorizontally), text = "Search driver", onClick = {})
        }
    }
}

@Composable
fun TextFieldLabel(
    label: String,
    input: String
) {
    var text by remember { mutableStateOf(input) }

    Column {
        Label(text = label)
        Box(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color.Black),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BasicTextField(
                    value = text,
                    onValueChange = { text = it },
                    textStyle = TextStyle(fontSize = 17.sp),
                    modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 15.dp, bottom = 15.dp),
                )
            }
        }
    }
    Spacer(modifier = Modifier.height(10.dp))
}


@Composable
fun DropDownLabel(
    label: String,
    options: List<String>,
    onOptionSelected: (String) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    var text by remember { mutableStateOf(options[0]) }

    Column {
        Label(text = label)
        Box(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color.Black)
                    .padding(start = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Content(modifier = Modifier
                    .weight(1f)
                    .clickable(onClick = { expanded = true }), text = text)
                IconButton(
                    onClick = { expanded = true },
                ) {
                    Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)
                }
            }
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(onClick = {
                    text = option
                    expanded = false
                    onOptionSelected(option)
                }) {
                    Text(text = option)
                }
            }
        }
    }
    Spacer(modifier = Modifier.height(10.dp))
}

