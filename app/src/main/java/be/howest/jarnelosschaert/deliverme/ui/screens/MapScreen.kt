package be.howest.jarnelosschaert.deliverme.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import be.howest.jarnelosschaert.deliverme.logic.models.Address
import be.howest.jarnelosschaert.deliverme.ui.helpers.functions.HandleLocationPermissions
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

@Composable
fun MapScreen(
    senderAddress: Address,
    receiverAddress: Address,
    onGoBack: () -> Unit,
) {
    var granted by remember { mutableStateOf(false) }
    HandleLocationPermissions(onPermission = {
        granted = it
    })
    Map(
        senderAddress = senderAddress,
        receiverAddress = receiverAddress,
        isMyLocationEnabled = granted,
        onGoBack = onGoBack,
    )
}

@Composable
fun Map(
    senderAddress: Address,
    receiverAddress: Address,
    isMyLocationEnabled: Boolean = true,
    onGoBack: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 50.dp)
    ) {
        val bruges = LatLng(51.2093, 3.2247)
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(bruges, 11f)
        }
        val uiSettings by remember {
            mutableStateOf(
                MapUiSettings(
                    zoomControlsEnabled = false,
                    compassEnabled = true,
                )
            )
        }
        val properties = MapProperties(
            isMyLocationEnabled = isMyLocationEnabled,
            maxZoomPreference = 16f,
            minZoomPreference = 9f
        )
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            properties = properties,
            uiSettings = uiSettings
        ) {
            Marker(
                state = MarkerState(LatLng(senderAddress.lat, senderAddress.lon)),
                title = "Sender Address",
                draggable = false,
            )
            Marker(
                state = MarkerState(LatLng(receiverAddress.lat, receiverAddress.lon)),
                title = "Receiver Address",
                draggable = false,
            )
        }
        TextButton(
            onClick = onGoBack, modifier = Modifier
                .align(Alignment.TopCenter)
        ) {
            Text(
                text = "< GO BACK",
                fontWeight = FontWeight.Bold
            )
        }
    }
}