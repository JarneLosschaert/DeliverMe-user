package be.howest.jarnelosschaert.deliverme.ui.screens.authScreens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import be.howest.jarnelosschaert.deliverme.ui.helpers.components.*

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    navigateToLogin: () -> Unit,
    signUp: () -> Unit
) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    Box(modifier = modifier.fillMaxWidth()) {
        Column {
            Title()
            SubTitle(text = "Create your account", modifier = Modifier.padding(top = 20.dp))
            AuthTextField(label = "Username", value = username, onValueChange = { username = it })
            AuthTextField(label = "Email", value = email, onValueChange = { email = it }, isEmail = true)
            AuthTextField(label = "Phone number", value = phone, onValueChange = { phone = it}, isPhone = true)
            AuthTextField(label = "Password", value = password, onValueChange = { password = it }, isPassword = true)
            AuthTextField(label = "Confirm password", value = confirmPassword, onValueChange = { confirmPassword = it }, isPassword = true)
            SmallButton(
                text = "Sign up",
                onClick = signUp,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally))
            AuthBottomNavigate(navigate = navigateToLogin, label = "Already have an account?", text = "Log in")
        }
    }
}