package com.example.tastetrail.network

import com.example.tastetrail.data.ConfirmPasswordResetRequest
import com.example.tastetrail.data.ConfirmPasswordResetResponse
import com.example.tastetrail.data.LoginRequest
import com.example.tastetrail.data.LoginResponse
import com.example.tastetrail.data.RegisterRequest
import com.example.tastetrail.data.RegisterResponse
import com.example.tastetrail.data.RequestPasswordResetRequest
import com.example.tastetrail.data.RequestPasswordResetResponse
import com.example.tastetrail.data.ChangePasswordRequest
import com.example.tastetrail.data.ChangePasswordResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST("login/")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @POST("register/")
    suspend fun register(@Body registerRequest: RegisterRequest): Response<RegisterResponse>

    @GET("profile/")
    suspend fun getProfile(): Response<RegisterResponse>

    @POST("password-reset/request/")
    suspend fun requestPasswordReset(@Body request: RequestPasswordResetRequest): Response<RequestPasswordResetResponse>

    @POST("password-reset/confirm/")
    suspend fun confirmPasswordReset(@Body request: ConfirmPasswordResetRequest): Response<ConfirmPasswordResetResponse>

    @POST("change-password/")
    suspend fun changePassword(@Body request: ChangePasswordRequest): Response<ChangePasswordResponse>
}
