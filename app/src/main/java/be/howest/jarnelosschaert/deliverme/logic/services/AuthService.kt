package be.howest.jarnelosschaert.deliverme.logic.services

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.howest.jarnelosschaert.deliverme.logic.models.Customer
import be.howest.jarnelosschaert.deliverme.logic.services.other.RetrofitInstance
import be.howest.jarnelosschaert.deliverme.logic.services.requests.AddContactRequest
import be.howest.jarnelosschaert.deliverme.logic.services.requests.LoginRequest
import be.howest.jarnelosschaert.deliverme.logic.services.requests.RegisterRequest
import be.howest.jarnelosschaert.deliverme.logic.services.requests.UpdateCustomerRequest
import be.howest.jarnelosschaert.deliverme.logic.services.responses.RegistrationLoginResponse
import kotlinx.coroutines.launch

class AuthService : ViewModel() {
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

    fun updateCustomer(
        jwt: String,
        id: Int,
        username: String,
        email: String,
        phone: String,
        street: String,
        number: String,
        zip: String,
        city: String,
        handleSuccess: (Customer) -> Unit,
        handleFailure: (String) -> Unit,
    ) {
        viewModelScope.launch {
            val updateCustomerRequest = UpdateCustomerRequest(
                username,
                email,
                phone,
                street,
                number,
                zip,
                city,
            )
            try {
                val response = apiService.updateCustomer("Bearer $jwt", id, updateCustomerRequest)
                handleSuccess(response)
            } catch (e: Exception) {
                handleFailure("Failed to update customer")
                println("Error update customer: ${e.message}")
            }
        }
    }

    fun deleteCustomer(
        jwt: String,
        id: Int,
        handleSuccess: () -> Unit,
        handleFailure: (String) -> Unit,
    ) {
        viewModelScope.launch {
            try {
                apiService.deleteCustomer("Bearer $jwt", id)
                handleSuccess()
            } catch (e: Exception) {
                handleFailure("Failed to delete customer")
                println("Error delete customer: ${e.message}")
            }
        }
    }

    fun addContact(
        jwt: String,
        email: String,
        handleSuccess: (Customer) -> Unit,
        handleFailure: (String) -> Unit,
    ) {
        viewModelScope.launch {
            val addContactRequest = AddContactRequest(email)
            try {
                val response = apiService.addContact("Bearer $jwt", addContactRequest)
                handleSuccess(response)
            } catch (e: Exception) {
                handleFailure("Failed to add contact")
                println("Error add contact: ${e.message}")
            }
        }
    }

    fun deleteContact(
        jwt: String,
        id: Int,
        handleSuccess: () -> Unit,
        handleFailure: (String) -> Unit,
    ) {
        viewModelScope.launch {
            try {
                apiService.deleteContact("Bearer $jwt", id)
                handleSuccess()
            } catch (e: Exception) {
                handleFailure("Failed to delete contact")
                println("Error delete contact: ${e.message}")
            }
        }
    }
}
