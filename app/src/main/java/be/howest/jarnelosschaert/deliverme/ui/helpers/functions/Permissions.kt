package be.howest.jarnelosschaert.deliverme.ui.helpers.functions

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat

@Composable
fun HandleLocationPermissions(onPermissionGranted: (Boolean) -> Unit) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            onPermissionGranted(true)
        } else {
            onPermissionGranted(false)
        }
    }

    if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION)
        == android.content.pm.PackageManager.PERMISSION_GRANTED) {
        onPermissionGranted(true)
    } else {
        LaunchedEffect(true) {
            launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }
}
