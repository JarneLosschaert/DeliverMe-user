package be.howest.jarnelosschaert.deliverme.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import be.howest.jarnelosschaert.deliverme.ui.helpers.functions.HandleLocationPermissions
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

@Composable
fun MapScreen(
    onGoBack: () -> Unit,
) {
    var granted by remember { mutableStateOf(false) }
    HandleLocationPermissions(onPermission = {
        granted = it
    })
    Map(isMyLocationEnabled = granted, onGoBack = onGoBack)
}

@Composable
fun Map(
    isMyLocationEnabled: Boolean = true,
    onGoBack: () -> Unit,
) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 30.dp)) {
        val bruges = LatLng(51.2093, 3.2247)
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(bruges, 11f)
        }
        val uiSettings by remember { mutableStateOf(MapUiSettings(
            zoomControlsEnabled = false,
        )) }
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
        )
        GoBackButton(onGoBack = onGoBack)
    }
}

@Composable
fun GoBackButton(
    onGoBack: () -> Unit
) {
    TextButton(onClick = onGoBack) {
        Text(
            text = "< Go back",
            textDecoration = TextDecoration.Underline,
            fontWeight = FontWeight.Bold
        )
    }
}