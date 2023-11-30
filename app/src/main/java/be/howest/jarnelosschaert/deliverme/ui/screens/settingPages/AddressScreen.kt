package be.howest.jarnelosschaert.deliverme.ui.screens.settingPages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import be.howest.jarnelosschaert.deliverme.ui.helpers.components.GeneralButton
import be.howest.jarnelosschaert.deliverme.ui.helpers.components.SubTitle
import be.howest.jarnelosschaert.deliverme.ui.helpers.components.TextFieldLabel
import be.howest.jarnelosschaert.deliverme.ui.helpers.components.Title

@Composable
fun AddressScreen(
    modifier: Modifier = Modifier,
    subtitle: String = "Address details",
    onGoBack: () -> Unit,
    onConfirmAddress: (String, String, String, String) -> Unit
) {
    val street = remember { mutableStateOf("") }
    val number = remember { mutableStateOf("") }
    val city = remember { mutableStateOf("") }
    val postalCode = remember { mutableStateOf("") }

    Box(modifier = modifier.fillMaxWidth()) {
        Column {
            Title(text = "Address", onGoBack = onGoBack, withGoBack = true)
            LazyColumn(content =
            {
                item {
                    SubTitle(text = subtitle)
                    TextFieldLabel(
                        label = "Street",
                        value = street.value,
                        onValueChange = { street.value = it })
                    TextFieldLabel(
                        label = "Number",
                        value = number.value,
                        onValueChange = { number.value = it })
                    TextFieldLabel(
                        label = "City",
                        value = city.value,
                        onValueChange = { city.value = it })
                    TextFieldLabel(
                        label = "Postal code",
                        value = postalCode.value,
                        onValueChange = { postalCode.value = it },
                        isNumber = true
                    )
                    GeneralButton(
                        text = "Use address", modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp, end = 10.dp),
                        onClick = {
                            onConfirmAddress(
                                street.value,
                                number.value,
                                city.value,
                                postalCode.value
                            )
                        }
                    )
                }
            })
        }
    }
}