package be.howest.jarnelosschaert.deliverme.logic.services

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.howest.jarnelosschaert.deliverme.logic.services.other.RetrofitInstance
import be.howest.jarnelosschaert.deliverme.logic.services.requests.LoginRequest
import be.howest.jarnelosschaert.deliverme.logic.services.requests.RegisterRequest
import be.howest.jarnelosschaert.deliverme.logic.services.responses.RegistrationLoginResponse
import kotlinx.coroutines.launch

class AuthService : ViewModel() {
    private val apiService = RetrofitInstance.apiService

    fun registerUser(
        username: String,
        email: String,
        phone: String,
        password: String,
        street: String,
        city: String,
        zip: String,
        number: String
    ) {
        val registerRequest =
            RegisterRequest(username, email, phone, password, street, city, zip, number)

        viewModelScope.launch {
            try {
                val response = apiService.registerUser(registerRequest)
                handleRegistrationResponse(response)
            } catch (e: Exception) {
                // Handle any exceptions that may occur
                println("Error signUp: ${e.message}")
            }
        }
    }
    fun loginUser(
        email: String,
        password: String
    ) {
        val loginRequest =
            LoginRequest(email, password)

        viewModelScope.launch {
            try {
                val response = apiService.loginUser(loginRequest)
                handleLoginResponse(response)
            } catch (e: Exception) {
                // Handle any exceptions that may occur
                println("Error login: ${e.message}")
            }
        }
    }

    private fun handleRegistrationResponse(response: RegistrationLoginResponse) {
        // Handle the response as needed
        println("JWT: ${response.jwt}")
        println("Customer ID: ${response.customer.id}")
        println("Customer Name: ${response.customer.person.name}")
        println("Customer Email: ${response.customer.person.email}")
        // Continue extracting other relevant information from the response
    }

    private fun handleLoginResponse(response: RegistrationLoginResponse) {
        // Handle the response as needed
        println("JWT: ${response.jwt}")
        println("Customer ID: ${response.customer.id}")
        println("Customer Name: ${response.customer.person.name}")
        println("Customer Email: ${response.customer.person.email}")
        // Continue extracting other relevant information from the response
    }
}
