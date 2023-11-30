import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.howest.jarnelosschaert.deliverme.logic.services.other.RetrofitInstance
import be.howest.jarnelosschaert.deliverme.logic.services.requests.RegisterRequest
import be.howest.jarnelosschaert.deliverme.logic.services.requests.RegistrationResponse
import kotlinx.coroutines.launch

class RegistrationViewModel : ViewModel() {

    private val apiService = RetrofitInstance.apiService

    fun registerUser(username: String, email: String, phone: String, password: String, street: String, city: String, zip: String, number: String) {
        val registerRequest = RegisterRequest(username, email, phone, password, street, city, zip, number)

        viewModelScope.launch {
            try {
                val response = apiService.registerUser(registerRequest)
                handleRegistrationResponse(response)
            } catch (e: Exception) {
                // Handle any exceptions that may occur
                println("Error retro: ${e.message}")
            }
        }
    }

    private fun handleRegistrationResponse(response: RegistrationResponse) {
        // Handle the response as needed
        println("JWT: ${response.jwt}")
        println("Customer ID: ${response.customer.id}")
        println("Customer Name: ${response.customer.person.name}")
        println("Customer Email: ${response.customer.person.email}")
        // Continue extracting other relevant information from the response
    }
}
