package be.howest.jarnelosschaert.deliverme.ui.screens

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import be.howest.jarnelosschaert.deliverme.R
import be.howest.jarnelosschaert.deliverme.logic.helpers.notifications.Location
import be.howest.jarnelosschaert.deliverme.logic.models.Address
import be.howest.jarnelosschaert.deliverme.ui.helpers.components.Content
import be.howest.jarnelosschaert.deliverme.ui.helpers.components.Title
import be.howest.jarnelosschaert.deliverme.ui.helpers.functions.HandleLocationPermissions
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

@Composable
fun MapScreen(
    senderAddress: Address,
    receiverAddress: Address,
    locationDriver: Location?,
    onGoBack: () -> Unit,
) {
    var granted by remember { mutableStateOf(false) }
    HandleLocationPermissions(onPermission = {
        granted = it
    })
    Map(
        senderAddress = senderAddress,
        receiverAddress = receiverAddress,
        locationDriver = locationDriver,
        isMyLocationEnabled = granted,
        onGoBack = onGoBack,
    )
}

@Composable
fun Map(
    senderAddress: Address,
    receiverAddress: Address,
    locationDriver: Location?,
    isMyLocationEnabled: Boolean = true,
    onGoBack: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 50.dp)
    ) {
        val senderAddressLocation = LatLng(senderAddress.lat, senderAddress.lon)
        val receiverAddressLocation = LatLng(receiverAddress.lat, receiverAddress.lon)
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(senderAddressLocation, 11f)
        }
        val properties = MapProperties(
            isMyLocationEnabled = isMyLocationEnabled,
            maxZoomPreference = 16f,
            minZoomPreference = 9f
        )
        Column {
            Box(modifier = Modifier.padding(horizontal = 15.dp)) {
                Title(text = "Map", onGoBack = onGoBack, withGoBack = true)
            }
            if (locationDriver == null) {
                Content(
                    text = "Waiting for the location of the driver...",
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    color = MaterialTheme.colors.primary,
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {

                GoogleMap(
                    modifier = Modifier.fillMaxSize(),
                    cameraPositionState = cameraPositionState,
                    properties = properties,
                ) {
                    Marker(
                        state = MarkerState(senderAddressLocation),
                        title = "Sender Address",
                    )
                    Marker(
                        state = MarkerState(receiverAddressLocation),
                        title = "Receiver Address",
                    )
                    if (locationDriver != null) {
                        val location = LatLng(locationDriver.latitude, locationDriver.longitude)
                        val icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)
                        Marker(
                            state = MarkerState(position = location),
                            title = "Driver",
                            icon = icon,
                        )
                    }
                }
            }
        }
    }
}
