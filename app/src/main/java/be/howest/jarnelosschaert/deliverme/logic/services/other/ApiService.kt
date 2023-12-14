package be.howest.jarnelosschaert.deliverme.logic.services.other

import be.howest.jarnelosschaert.deliverme.logic.services.requests.CreatePackageRequest
import be.howest.jarnelosschaert.deliverme.logic.services.requests.LoginRequest
import be.howest.jarnelosschaert.deliverme.logic.services.requests.RegisterRequest
import be.howest.jarnelosschaert.deliverme.logic.services.responses.RegistrationLoginResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {
    @POST("/customers")
    suspend fun registerUser(@Body registerRequest: RegisterRequest): RegistrationLoginResponse

    @POST("/customers/auth")
    suspend fun loginUser(@Body loginRequest: LoginRequest): RegistrationLoginResponse

    @GET("/packages")
    suspend fun getPackages(@Header("Authorization") authToken: String): List<Package>

    @POST("/packages")
    suspend fun createPackage(
        @Header("Authorization") authToken: String,
        @Body packageRequest: CreatePackageRequest
    ): Package
}
