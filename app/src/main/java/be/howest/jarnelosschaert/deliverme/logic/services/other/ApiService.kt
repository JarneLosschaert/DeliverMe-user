import be.howest.jarnelosschaert.deliverme.logic.services.requests.LoginRequest
import be.howest.jarnelosschaert.deliverme.logic.services.requests.RegisterRequest
import be.howest.jarnelosschaert.deliverme.logic.services.responses.RegistrationLoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("/customer/auth/register")
    suspend fun registerUser(@Body registerRequest: RegisterRequest): RegistrationLoginResponse

    @POST("/customer/auth/login")
    suspend fun loginUser(@Body loginRequest: LoginRequest): RegistrationLoginResponse
}
