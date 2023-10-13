package be.howest.jarnelosschaert.deliverme.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.magnifier
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import be.howest.jarnelosschaert.deliverme.R
import be.howest.jarnelosschaert.deliverme.ui.helpers.*

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {
    // text in the center
    Box(modifier = modifier.fillMaxWidth()) {
        Column {
            Title()
            HomeButtons()
            SubTitle(text = "Active Deliveries")
            Delivery()
            SubTitle(text = "History")
        }
    }
}

@Composable
fun HomeButtons() {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        HomeButton(
            onClick = {},
            text = "Send a package",
            image = R.drawable.box
        )
        HomeButton(
            onClick = {},
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
                    .height(50.dp)
                    .padding(5.dp),
                colorFilter = ColorFilter.tint(MaterialTheme.colors.onPrimary)
            )
            Text(text = text, color = MaterialTheme.colors.onPrimary, fontSize = 14.sp)
        }
    }
}
@Composable
fun Delivery() {
    Box(
        modifier = Modifier
            .padding(start = 8.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color.Black)
                .clip(MaterialTheme.shapes.medium)
                .padding(8.dp)
                .height(100.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Label(text = "Sender")
                Content(text = "You")
                Spacer(modifier = Modifier.height(10.dp))
                Label(text = "Receiver")
                Content(text = "Glenn Callens")
            }
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.End
            ) {
                SmallButton(onClick = {})
                DateDetails(text = "12/10/2022")
            }
        }
    }
}

@Composable
fun SubTitle(text: String) {
    Text(
        text = text,
        modifier = Modifier.padding(top = 10.dp, bottom = 10.dp),
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    )
}

@Composable
@Preview
fun HomeScreenPreview() {
    HomeScreen()
}