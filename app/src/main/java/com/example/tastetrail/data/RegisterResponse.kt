package com.example.tastetrail.data

import kotlinx.serialization.Serializable

@Serializable
data class RegisterResponse(
    val success: Boolean,
    val message: String,
    val data: RegisterData? = null
)

@Serializable
data class RegisterData(
    val user: UserData
)
