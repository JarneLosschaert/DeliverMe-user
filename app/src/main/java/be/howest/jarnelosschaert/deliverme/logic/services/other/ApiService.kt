package be.howest.jarnelosschaert.deliverme.logic.services.other

import be.howest.jarnelosschaert.deliverme.logic.models.Customer
import be.howest.jarnelosschaert.deliverme.logic.services.requests.AddContactRequest
import be.howest.jarnelosschaert.deliverme.logic.services.requests.CreatePackageRequest
import be.howest.jarnelosschaert.deliverme.logic.services.requests.LoginRequest
import be.howest.jarnelosschaert.deliverme.logic.services.requests.RegisterRequest
import be.howest.jarnelosschaert.deliverme.logic.services.responses.RegistrationLoginResponse
import retrofit2.http.*

interface ApiService {
    @POST("/customers")
    suspend fun registerUser(@Body registerRequest: RegisterRequest): RegistrationLoginResponse

    @POST("/customers/auth")
    suspend fun loginUser(@Body loginRequest: LoginRequest): RegistrationLoginResponse

    @DELETE("/customers/{id}")
    suspend fun deleteCustomer(
        @Header("Authorization") authToken: String,
        @Path("id") customerId: Int,
    )

    @POST("/customers/contacts")
    suspend fun addContact(
        @Header("Authorization") authToken: String,
        @Body addContactRequest: AddContactRequest
    ): Customer

    @GET("/packages")
    suspend fun getPackages(@Header("Authorization") authToken: String): List<Package>

    @POST("/packages")
    suspend fun createPackage(
        @Header("Authorization") authToken: String,
        @Body packageRequest: CreatePackageRequest
    ): Package
}
