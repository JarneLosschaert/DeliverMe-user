package be.howest.jarnelosschaert.deliverme.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import be.howest.jarnelosschaert.deliverme.R
import be.howest.jarnelosschaert.deliverme.logic.models.Delivery
import be.howest.jarnelosschaert.deliverme.ui.helpers.components.*

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    paidDeliveries: List<Delivery>,
    assignedDeliveries: List<Delivery>,
    transitDeliveries: List<Delivery>,
    deliveredDeliveries: List<Delivery>,
    onDeliveryClick: (Delivery) -> Unit,
    navigateDeliver: () -> Unit,
    navigateContacts: () -> Unit,
) {
    Box(modifier = modifier.fillMaxWidth()) {
        Column {
            Title()
            HomeButtons(navigateDeliver = navigateDeliver, navigateContacts = navigateContacts)
            LazyColumn(content = {
                item {
                    if (paidDeliveries.isNotEmpty()) {
                        SubTitle(text = "Searching for a driver...")
                        for (delivery in paidDeliveries) {
                            Delivery(
                                delivery = delivery,
                                onDeliveryClick = onDeliveryClick
                            )
                        }
                    }
                    if (assignedDeliveries.isNotEmpty()) {
                        SubTitle(text = "Assigned deliveries")
                        for (delivery in assignedDeliveries) {
                            Delivery(
                                delivery = delivery,
                                onDeliveryClick = onDeliveryClick
                            )
                        }
                    }
                    if (transitDeliveries.isNotEmpty()) {
                        SubTitle(text = "Deliveries in transit")
                        for (delivery in transitDeliveries) {
                            Delivery(
                                delivery = delivery,
                                onDeliveryClick = onDeliveryClick
                            )
                        }
                    }
                    SubTitle(text = "History")
                    if (deliveredDeliveries.isNotEmpty()) {
                        for (delivery in deliveredDeliveries) {
                            Delivery(
                                delivery = delivery,
                                onDeliveryClick = onDeliveryClick
                            )
                        }
                    } else {
                        Content(text = "No deliveries yet")
                    }
                }
            })
        }
    }
}

@Composable
fun HomeButtons(
    navigateDeliver: () -> Unit,
    navigateContacts: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        HomeButton(
            onClick = navigateDeliver,
            text = "Send a package",
            image = R.drawable.delivery
        )
        HomeButton(
            onClick = navigateContacts,
            text = "Contacts",
            image = R.drawable.contacts
        )
    }
}

@Composable
fun HomeButton(
    onClick: () -> Unit = {},
    text: String,
    image: Int
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .clip(RoundedCornerShape(15.dp))
    ) {
        Column(
            modifier = Modifier
                .height(80.dp)
                .width(120.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Image(
                painter = painterResource(id = image),
                contentDescription = text,
                modifier = Modifier
                    .height(45.dp),
                colorFilter = ColorFilter.tint(MaterialTheme.colors.onPrimary)
            )
            Text(text = text, color = MaterialTheme.colors.onPrimary, fontSize = 14.sp)
        }
    }
}

@Composable
fun Delivery(
    modifier: Modifier = Modifier,
    delivery: Delivery,
    onDeliveryClick: (Delivery) -> Unit,
) {
    Box(
        modifier = modifier
            .padding(start = 8.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
                .clip(MaterialTheme.shapes.medium)
                .padding(8.dp)
                .height(95.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Label(text = "Sender")
                Content(text = delivery.packageInfo.sender.person.name)
                Spacer(modifier = Modifier.height(10.dp))
                Label(text = "Receiver")
                Content(text = delivery.packageInfo.receiver.person.name)
            }
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.End
            ) {
                GeneralButton(text = "Details", onClick = { onDeliveryClick(delivery) })
                DateDetails(text = "12/10/2022")
            }
        }
    }
    Spacer(modifier = Modifier.height(10.dp))
}
