package be.howest.jarnelosschaert.deliverme.ui.screens.authScreens

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import be.howest.jarnelosschaert.deliverme.ui.helpers.components.*

@Composable
fun LoginScreen() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxWidth()) {
        Column {
            Title()
            SubTitle(text = "Login details", modifier = Modifier.padding(top = 20.dp))
            LoginTextField(label = "Email", value = email, onValueChange = { email = it })
            LoginTextField(label = "Password", value = password, onValueChange = { password = it }, isPassword = true)
            SmallButton(
                text = "Log in",
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally))
            SignUp()
        }
    }
}

@Composable
fun LoginTextField(label: String, value: String, onValueChange: (String) -> Unit, isPassword: Boolean = false) {
    Label(text = label)
    GeneralTextField(text = value, onValueChange = onValueChange, isPassword = isPassword)
    Spacer(modifier = Modifier.padding(top = 10.dp))
}

@Composable
fun SignUp() {
    Spacer(modifier = Modifier.height(30.dp))
    // space between
    Row(modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Label(text = "Don't have an account?")
        SmallButton(text = "Sign up", onClick = { /*TODO*/ }, modifier = Modifier.padding(start = 30.dp))
    }
}