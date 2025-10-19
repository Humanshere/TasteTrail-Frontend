package com.example.tastetrail.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val success: Boolean,
    val message: String,
    val data: LoginData? = null
)

@Serializable
data class LoginData(
    val tokens: Tokens,
    val user: UserData
)

@Serializable
data class Tokens(
    val access: String,
    val refresh: String
)

@Serializable
data class UserData(
    val id: String,
    val email: String,
    val username: String,
    val role: String,
    @SerialName("is_active")
    val isActive: Boolean? = null,
    @SerialName("created_at")
    val createdAt: String? = null,
    @SerialName("updated_at")
    val updatedAt: String? = null,
    @SerialName("last_login")
    val lastLogin: String? = null
)
