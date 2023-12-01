package be.howest.jarnelosschaert.deliverme.logic.services

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.howest.jarnelosschaert.deliverme.logic.services.other.RetrofitInstance
import be.howest.jarnelosschaert.deliverme.logic.services.requests.LoginRequest
import be.howest.jarnelosschaert.deliverme.logic.services.requests.RegisterRequest
import be.howest.jarnelosschaert.deliverme.logic.services.responses.RegistrationLoginResponse
import kotlinx.coroutines.launch

class AuthService: ViewModel() {
    private val apiService = RetrofitInstance.apiService

    fun signUp(
        username: String,
        email: String,
        phone: String,
        password: String,
        street: String,
        number: String,
        zip: String,
        city: String,
        handleSuccess: (RegistrationLoginResponse) -> Unit,
        handleFailure: (String) -> Unit,
    ) {
        viewModelScope.launch {
            val registerRequest = RegisterRequest(
                username,
                email,
                phone,
                password,
                street,
                number,
                zip,
                city,
            )
            try {
                val response = apiService.registerUser(registerRequest)
                handleSuccess(response)
            } catch (e: Exception) {
                handleFailure("Failed to register")
                println("Error register: ${e.message}")
            }
        }
    }

    fun login(
        email: String, password: String,
        handleSuccess: (RegistrationLoginResponse) -> Unit,
        handleFailure: (String) -> Unit,
    ) {
        viewModelScope.launch {
            val loginRequest = LoginRequest(email, password)
            try {
                val response = apiService.loginUser(loginRequest)
                handleSuccess(response)
            } catch (e: Exception) {
                handleFailure("Failed to login")
                println("Error login: ${e.message}")
            }
        }
    }
}

data class LoginResult(
    var success: Boolean = false,
    var response: RegistrationLoginResponse? = null,
    var error: String = ""
)
