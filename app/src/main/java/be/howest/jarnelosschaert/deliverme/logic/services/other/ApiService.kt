package be.howest.jarnelosschaert.deliverme.logic.services.other

import be.howest.jarnelosschaert.deliverme.logic.models.Customer
import be.howest.jarnelosschaert.deliverme.logic.models.Delivery
import be.howest.jarnelosschaert.deliverme.logic.models.Package
import be.howest.jarnelosschaert.deliverme.logic.services.requests.*
import be.howest.jarnelosschaert.deliverme.logic.services.responses.PayResponse
import be.howest.jarnelosschaert.deliverme.logic.services.responses.RegistrationLoginResponse
import retrofit2.http.*

interface ApiService {
    @POST("/customers")
    suspend fun registerUser(@Body registerRequest: RegisterRequest): RegistrationLoginResponse

    @POST("/customers/auth")
    suspend fun loginUser(@Body loginRequest: LoginRequest): RegistrationLoginResponse

    @PUT("/customers/auth")
    suspend fun changePassword(
        @Header("Authorization") authToken: String,
        @Body updatePasswordRequest: UpdatePasswordRequest,
    )

    @PUT("/customers/{id}")
    suspend fun updateCustomer(
        @Header("Authorization") authToken: String,
        @Path("id") customerId: Int,
        @Body updateCustomerRequest: UpdateCustomerRequest,
    ): Customer

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

    @GET("/deliveries")
    suspend fun getDeliveries(@Header("Authorization") authToken: String): List<Delivery>

    @DELETE("/customers/contacts/{id}")
    suspend fun deleteContact(
        @Header("Authorization") authToken: String,
        @Path("id") contactId: Int,
    )

    @POST("/packages")
    suspend fun createPackage(
        @Header("Authorization") authToken: String,
        @Body packageRequest: CreatePackageRequest
    ): Package

    @GET("/packages/{id}/pay")
    suspend fun getPaymentIntent(
        @Header("Authorization") authToken: String,
        @Path("id") packageId: Int,
    ): PayResponse
}
