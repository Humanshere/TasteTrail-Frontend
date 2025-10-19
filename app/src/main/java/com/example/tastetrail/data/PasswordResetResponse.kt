package com.example.tastetrail.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// This class matches the response for a successful password reset *request*
@Serializable
data class RequestPasswordResetResponse(
    val success: Boolean,
    val message: String,
    val data: PasswordResetData?
)

@Serializable
data class PasswordResetData(
    val token: String,
    @SerialName("expires_in_minutes")
    val expiresInMinutes: Int
)

// This class matches the response for a successful password reset *confirmation*
@Serializable
data class ConfirmPasswordResetResponse(
    val success: Boolean,
    val message: String
)
