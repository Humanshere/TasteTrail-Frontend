package com.example.tastetrail.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tastetrail.data.ConfirmPasswordResetRequest
import com.example.tastetrail.data.ErrorResponse
import com.example.tastetrail.data.LoginRequest
import com.example.tastetrail.data.RegisterRequest
import com.example.tastetrail.data.RequestPasswordResetRequest
import com.example.tastetrail.data.SessionManager
import com.example.tastetrail.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class AuthViewModel : ViewModel() {

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState

    private val _registerState = MutableStateFlow<RegisterState>(RegisterState.Idle)
    val registerState: StateFlow<RegisterState> = _registerState

    private val _passwordResetState = MutableStateFlow<PasswordResetState>(PasswordResetState.Idle)
    val passwordResetState: StateFlow<PasswordResetState> = _passwordResetState

    private val json = Json { ignoreUnknownKeys = true }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading
            try {
                val response = RetrofitInstance.api.login(LoginRequest(email, password))
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    loginResponse?.data?.tokens?.access?.let {
                        SessionManager.authToken = it
                    }
                    _loginState.value = LoginState.Success
                } else {
                    val errorBody = response.errorBody()?.string()
                    val errorMessage = if (errorBody != null) {
                        try {
                            json.decodeFromString<ErrorResponse>(errorBody).error
                        } catch (e: Exception) {
                            "Invalid email or password"
                        }
                    } else {
                        "Invalid email or password"
                    }
                    _loginState.value = LoginState.Error(errorMessage)
                }
            } catch (e: Exception) {
                _loginState.value = LoginState.Error("An error occurred")
            }
        }
    }

    fun register(email: String, username: String, password: String) {
        viewModelScope.launch {
            _registerState.value = RegisterState.Loading
            try {
                val response = RetrofitInstance.api.register(RegisterRequest(email, username, password))
                if (response.isSuccessful) {
                    _registerState.value = RegisterState.Success
                } else {
                    val errorBody = response.errorBody()?.string()
                    val errorMessage = if (errorBody != null) {
                        try {
                            json.decodeFromString<ErrorResponse>(errorBody).error
                        } catch (e: Exception) {
                            "Registration failed"
                        }
                    } else {
                        "Registration failed"
                    }
                    _registerState.value = RegisterState.Error(errorMessage)
                }
            } catch (e: Exception) {
                _registerState.value = RegisterState.Error("An error occurred")
            }
        }
    }

    fun requestPasswordReset(email: String) {
        viewModelScope.launch {
            _passwordResetState.value = PasswordResetState.Loading
            try {
                val response = RetrofitInstance.api.requestPasswordReset(RequestPasswordResetRequest(email))
                if (response.isSuccessful) {
                    _passwordResetState.value = PasswordResetState.RequestSuccess
                } else {
                    _passwordResetState.value = PasswordResetState.Error("Failed to send request")
                }
            } catch (e: Exception) {
                _passwordResetState.value = PasswordResetState.Error("An error occurred")
            }
        }
    }

    fun confirmPasswordReset(token: String, newPassword: String) {
        viewModelScope.launch {
            _passwordResetState.value = PasswordResetState.Loading
            try {
                val response = RetrofitInstance.api.confirmPasswordReset(ConfirmPasswordResetRequest(token, newPassword))
                if (response.isSuccessful) {
                    _passwordResetState.value = PasswordResetState.ConfirmSuccess
                } else {
                    _passwordResetState.value = PasswordResetState.Error("Failed to reset password")
                }
            } catch (e: Exception) {
                _passwordResetState.value = PasswordResetState.Error("An error occurred")
            }
        }
    }
}

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    object Success : LoginState()
    data class Error(val message: String) : LoginState()
}

sealed class RegisterState {
    object Idle : RegisterState()
    object Loading : RegisterState()
    object Success : RegisterState()
    data class Error(val message: String) : RegisterState()
}

sealed class PasswordResetState {
    object Idle : PasswordResetState()
    object Loading : PasswordResetState()
    object RequestSuccess : PasswordResetState()
    object ConfirmSuccess : PasswordResetState()
    data class Error(val message: String) : PasswordResetState()
}
