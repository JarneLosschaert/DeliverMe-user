package be.howest.jarnelosschaert.deliverme.ui.helpers

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Title(text: String = "DeliverMe") {
    Box(
        modifier = Modifier.fillMaxWidth()) {
        Text(
            text = text,
            color = MaterialTheme.colors.primary,
            fontSize = 35.sp,
            modifier = Modifier
                .padding(15.dp)
                .align(Alignment.Center),
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
fun Label(text: String) {
    Text(
        text = text,
        color = MaterialTheme.colors.onSurface,
        fontSize = 15.sp,
        modifier = Modifier.padding(0.dp)
    )
}

@Composable
fun Content(text: String) {
    Text(
        text = text,
        color = MaterialTheme.colors.onBackground,
        fontSize = 17.sp,
    )
}

@Composable
fun DateDetails(text: String) {
    Text(
        text = text,
        color = MaterialTheme.colors.onBackground,
        fontSize = 15.sp,
        textAlign = TextAlign.End,
        modifier = Modifier.fillMaxWidth(),
        fontStyle = FontStyle.Italic
    )
}

@Composable
fun SmallButton(
    text: String = "See details",
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .clip(RoundedCornerShape(15.dp))
    ) {
        Text(text = text, fontSize = 15.sp)
    }
}
