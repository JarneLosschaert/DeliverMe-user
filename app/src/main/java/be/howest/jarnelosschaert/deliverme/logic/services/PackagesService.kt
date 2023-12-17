package be.howest.jarnelosschaert.deliverme.logic.services

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.howest.jarnelosschaert.deliverme.logic.models.Address
import be.howest.jarnelosschaert.deliverme.logic.models.Package
import be.howest.jarnelosschaert.deliverme.logic.models.PackageSize
import be.howest.jarnelosschaert.deliverme.logic.services.other.RetrofitInstance
import be.howest.jarnelosschaert.deliverme.logic.services.requests.CreatePackageRequest
import be.howest.jarnelosschaert.deliverme.logic.services.responses.PayResponse
import kotlinx.coroutines.launch

class PackagesService: ViewModel() {
    private val apiService = RetrofitInstance.apiService

    /*
    fun getPackages(
        jwt: String,
        handleSuccess: (List<Package>) -> Unit,
        handleFailure: (String) -> Unit,
    ) {
        viewModelScope.launch {
            try {
                val response = apiService.getPackages("Bearer $jwt")
                handleSuccess(response)
            } catch (e: Exception) {
                handleFailure("Failed to get packages")
                println("Error get packages: ${e.message}")
            }
        }
    }

     */

    fun createPackage(
        jwt: String,
        receiverId: Int,
        senderAddress: Address,
        receiverAddress: Address,
        packageSize: PackageSize,
        description: String,
        handleSuccess: (Package) -> Unit,
        handleFailure: (String) -> Unit,
    ) {
        viewModelScope.launch {
            val createPackageRequest = CreatePackageRequest(
                receiverId,
                senderAddress,
                receiverAddress,
                packageSize,
                description
            )
            try {
                val response = apiService.createPackage("Bearer $jwt", createPackageRequest)
                handleSuccess(response)
            } catch (e: Exception) {
                handleFailure("Failed to create package")
                println("Error create package: ${e.message}")
            }
        }
    }

    fun getPaymentIntent(
        jwt: String,
        id: Int,
        handleSuccess: (PayResponse) -> Unit,
        handleFailure: (String) -> Unit,
    ) {
        viewModelScope.launch {
            try {
                val response = apiService.getPaymentIntent("Bearer $jwt", id)
                handleSuccess(response)
            } catch (e: Exception) {
                handleFailure("Failed to get payment intent")
                println("Error get payment intent: ${e.message}")
            }
        }
    }
}