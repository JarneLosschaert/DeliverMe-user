package be.howest.jarnelosschaert.deliverme.logic

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import be.howest.jarnelosschaert.deliverme.logic.data.defaultCustomer
import be.howest.jarnelosschaert.deliverme.logic.models.Customer
import be.howest.jarnelosschaert.deliverme.logic.models.Address
import be.howest.jarnelosschaert.deliverme.logic.models.Person

class AuthUiState : ViewModel() {
    var jwt by mutableStateOf("")
    var customer by  mutableStateOf(defaultCustomer)
    var loginErrors by mutableStateOf(emptyList<String>())
    var signUpErrors by mutableStateOf(emptyList<String>())
    var addressErrors by mutableStateOf(emptyList<String>())
}