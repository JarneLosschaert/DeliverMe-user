import be.howest.jarnelosschaert.deliverme.logic.services.requests.RegisterRequest
import be.howest.jarnelosschaert.deliverme.logic.services.requests.RegistrationResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("/customer/auth/register")
    suspend fun registerUser(@Body registerRequest: RegisterRequest): RegistrationResponse
}
