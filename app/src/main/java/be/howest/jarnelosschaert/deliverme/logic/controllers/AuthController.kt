package be.howest.jarnelosschaert.deliverme.logic.controllers

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import be.howest.jarnelosschaert.deliverme.MainActivity
import be.howest.jarnelosschaert.deliverme.logic.helpers.checkAddress
import be.howest.jarnelosschaert.deliverme.logic.helpers.checkValuesSignUp
import be.howest.jarnelosschaert.deliverme.logic.AuthUiState
import be.howest.jarnelosschaert.deliverme.logic.data.defaultCustomer
import be.howest.jarnelosschaert.deliverme.logic.models.Address
import be.howest.jarnelosschaert.deliverme.logic.data.SignUp
import be.howest.jarnelosschaert.deliverme.logic.services.AuthService
import be.howest.jarnelosschaert.deliverme.logic.services.responses.RegistrationLoginResponse
import be.howest.jarnelosschaert.deliverme.ui.AuthorizeScreens

class AuthController(
    private val navController: NavController
) {
    private val authService = AuthService()
    val uiState = AuthUiState()

    private var _signUp: SignUp = SignUp("", "", "", "", "")

    fun login(email: String, password: String) {
        clearErrors()
        authService.login(
            email,
            password,
            { handleLoginSignUpSuccess(it) },
            { handleLoginFailure(it) })
    }

    fun updateCustomer(
        name: String? = null,
        email: String? = null,
        phone: String? = null,
        address: Address? = null
    ) {
        val updatedPerson = uiState.customer.person.copy(
            name = name ?: uiState.customer.person.name,
            email = email ?: uiState.customer.person.email,
            phone = phone ?: uiState.customer.person.phone
        )
        val updatedCustomer = uiState.customer.copy(
            person = updatedPerson,
            homeAddress = address ?: uiState.customer.homeAddress
        )
        authService.updateCustomer(
            uiState.jwt,
            updatedCustomer.id,
            updatedCustomer.person.name,
            updatedCustomer.person.email,
            updatedCustomer.person.phone,
            updatedCustomer.homeAddress.street,
            updatedCustomer.homeAddress.number,
            updatedCustomer.homeAddress.zip,
            updatedCustomer.homeAddress.city,
            handleSuccess = {
                uiState.customer = updatedCustomer
                Toast.makeText(navController.context, "Account updated", Toast.LENGTH_SHORT).show()
            },
            handleFailure = {
                Toast.makeText(navController.context, "Account update failed", Toast.LENGTH_SHORT).show()
            }
        )
    }

    fun changePassword(password: String) {
        authService.changePassword(
            uiState.jwt,
            password,
            handleSuccess = {
                Toast.makeText(navController.context, "Password changed", Toast.LENGTH_SHORT).show()
            },
            handleFailure = {
                Toast.makeText(navController.context, "Password change failed", Toast.LENGTH_SHORT).show()
            }
        )
    }

    fun logout() {
        navController.navigate(AuthorizeScreens.Login.route)
        uiState.jwt = ""
        uiState.customer = defaultCustomer
        Toast.makeText(navController.context, "Logged out", Toast.LENGTH_SHORT).show()
    }

    fun checkSignUp(signUp: SignUp) {
        clearErrors()
        val errors = checkValuesSignUp(
            signUp.username,
            signUp.email,
            signUp.phone,
            signUp.password,
            signUp.confirmPassword
        )
        if (errors.isEmpty()) {
            _signUp = signUp
            navController.navigate(AuthorizeScreens.Address.route)
            uiState.signUpErrors = errors
        }
        uiState.signUpErrors = errors
    }

    fun signUp(address: Address) {
        clearErrors()
        val errors = checkAddress(address)
        uiState.addressErrors = errors
        if (errors.isEmpty()) {
            authService.signUp(
                _signUp.username,
                _signUp.email,
                _signUp.phone,
                _signUp.password,
                address.street,
                address.number,
                address.zip,
                address.city,
                { handleLoginSignUpSuccess(it) },
                { handleSignUpFailure(it) }
            )
        }
    }

    fun deleteCustomer() {
        authService.deleteCustomer(uiState.jwt, uiState.customer.id,
            handleSuccess = {
                logout()
                Toast.makeText(navController.context, "Account deleted", Toast.LENGTH_SHORT).show()
            },
            handleFailure = {
                Toast.makeText(navController.context, "Failed", Toast.LENGTH_SHORT).show()
            }
        )
    }

    private fun handleLoginSignUpSuccess(response: RegistrationLoginResponse) {
        uiState.jwt = response.jwt
        uiState.customer = response.customer
        navController.navigate(AuthorizeScreens.App.route)
        clearErrors()
    }
    private fun handleLoginFailure(error: String) {
        uiState.loginErrors = listOf(error)
    }

    private fun handleSignUpFailure(error: String) {
        uiState.signUpErrors = listOf(error)
    }

    private fun clearErrors() {
        uiState.loginErrors = emptyList()
        uiState.signUpErrors = emptyList()
    }
}