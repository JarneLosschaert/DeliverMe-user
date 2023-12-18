package be.howest.jarnelosschaert.deliverme.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import be.howest.jarnelosschaert.deliverme.logic.models.Address
import be.howest.jarnelosschaert.deliverme.logic.models.Customer
import be.howest.jarnelosschaert.deliverme.logic.models.PackageSize
import be.howest.jarnelosschaert.deliverme.ui.helpers.components.*
import be.howest.jarnelosschaert.deliverme.ui.helpers.functions.showAddress

@Composable
fun DeliverScreen(
    modifier: Modifier = Modifier,
    packageErrors: List<String>,
    contacts: List<Customer>,
    senderAddress: Address,
    receiver: Customer,
    receiverAddress: Address,
    packageSize: PackageSize,
    description: String,
    onGoBack: () -> Unit,
    onSenderAddressChange: () -> Unit,
    onReceiverChange: (Customer) -> Unit,
    onReceiverAddressChange: () -> Unit,
    onPackageSizeChange: (PackageSize) -> Unit,
    onDescriptionChange: (String) -> Unit,
    createPackage: () -> Unit,
) {
    Box(modifier = modifier.fillMaxWidth()) {
        Column {
            Title(onGoBack = onGoBack, withGoBack = true)
            SubTitle(text = "Delivery details")
            ChooseAddressLabel(
                label = "Address (sender)",
                address = senderAddress,
                onAddressChange = onSenderAddressChange
            )
            DropDownContacts(
                label = "Receiver",
                contacts = contacts,
                receiver = receiver,
                onContactSelected = onReceiverChange
            )
            ChooseAddressLabel(
                label = "Address (receiver)",
                address = receiverAddress,
                onAddressChange = onReceiverAddressChange
            )
            DropDownPackageSize(
                label = "Package size",
                sizes = PackageSize.values(),
                size = packageSize,
                onSizeSelected = onPackageSizeChange
            )
            TextFieldLabel(
                label = "Description",
                value = description,
                onValueChange = onDescriptionChange
            )
            Errors(errors = packageErrors)
            GeneralButton(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp),
                text = "Make delivery",
                onClick = createPackage
            )
        }
    }
}

@Composable
fun ChooseAddressLabel(label: String, address: Address, onAddressChange: () -> Unit) {
    Label(text = label)
    Box(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color.Black)
                .padding(start = 10.dp, top = 5.dp, bottom = 5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Content(text = showAddress(address), modifier = Modifier.weight(1f))
            IconButton(
                onClick = onAddressChange,
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        }
    }
    Spacer(modifier = Modifier.height(10.dp))
}

@Composable
fun DropDownContacts(
    label: String,
    contacts: List<Customer>,
    receiver: Customer,
    onContactSelected: (Customer) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }

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
                Content(
                    modifier = Modifier
                        .weight(1f)
                        .clickable(onClick = { expanded = true }), text = receiver.person.name
                )
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
            contacts.forEach { option ->
                DropdownMenuItem(onClick = {
                    expanded = false
                    onContactSelected(option)
                }) {
                    Text(text = option.person.name)
                }
            }
        }
    }
    Spacer(modifier = Modifier.height(10.dp))
}

@Composable
fun DropDownPackageSize(
    label: String,
    sizes: Array<PackageSize>,
    size: PackageSize,
    onSizeSelected: (PackageSize) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }

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
                Content(
                    modifier = Modifier
                        .weight(1f)
                        .clickable(onClick = { expanded = true }), text = size.value
                )
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
            sizes.forEach { option ->
                DropdownMenuItem(onClick = {
                    expanded = false
                    onSizeSelected(option)
                }) {
                    Text(text = option.value)
                }
            }
        }
    }
    Spacer(modifier = Modifier.height(10.dp))
}

