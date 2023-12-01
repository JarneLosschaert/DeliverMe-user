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
import be.howest.jarnelosschaert.deliverme.logic.models.HomeAddress
import be.howest.jarnelosschaert.deliverme.ui.helpers.components.*

@Composable
fun AddressScreen(
    modifier: Modifier = Modifier,
    subtitle: String = "Address details",
    errors: List<String>,
    onGoBack: () -> Unit,
    onConfirmAddress: (HomeAddress) -> Unit
) {
    val street = remember { mutableStateOf("") }
    val number = remember { mutableStateOf("") }
    val city = remember { mutableStateOf("") }
    val zip = remember { mutableStateOf("") }

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
                        label = "Zip",
                        value = zip.value,
                        onValueChange = { zip.value = it },
                        isNumber = true
                    )
                    TextFieldLabel(
                        label = "City",
                        value = city.value,
                        onValueChange = { city.value = it })
                    Errors(errors = errors)
                    GeneralButton(
                        text = "Use address", modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp, end = 10.dp),
                        onClick = {
                            onConfirmAddress(
                                HomeAddress(
                                    id = -1,
                                    street = street.value,
                                    number = number.value,
                                    zip = zip.value,
                                    city = city.value,
                                )
                            )
                        }
                    )
                }
            })
        }
    }
}