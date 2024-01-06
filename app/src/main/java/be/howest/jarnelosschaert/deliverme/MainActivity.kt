package be.howest.jarnelosschaert.deliverme

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import be.howest.jarnelosschaert.deliverme.ui.Authorize
import be.howest.jarnelosschaert.deliverme.ui.helpers.functions.HandleNotificationPermissions
import be.howest.jarnelosschaert.deliverme.ui.theme.DeliverMeTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DeliverMeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Authorize()
                }
            }
        }
    }
}