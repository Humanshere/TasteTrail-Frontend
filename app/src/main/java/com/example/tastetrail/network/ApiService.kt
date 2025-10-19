package com.example.tastetrail.network

import com.example.tastetrail.data.ConfirmPasswordResetRequest
import com.example.tastetrail.data.LoginRequest
import com.example.tastetrail.data.LoginResponse
import com.example.tastetrail.data.PasswordResetResponse
import com.example.tastetrail.data.RegisterRequest
import com.example.tastetrail.data.RegisterResponse
import com.example.tastetrail.data.RequestPasswordResetRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("login/")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @POST("register/")
    suspend fun register(@Body registerRequest: RegisterRequest): Response<RegisterResponse>

    @POST("password-reset/request/")
    suspend fun requestPasswordReset(@Body request: RequestPasswordResetRequest): Response<PasswordResetResponse>

    @POST("password-reset/confirm/")
    suspend fun confirmPasswordReset(@Body request: ConfirmPasswordResetRequest): Response<PasswordResetResponse>
}
