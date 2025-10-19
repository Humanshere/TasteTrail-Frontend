package com.example.tastetrail.data

import kotlinx.serialization.Serializable

@Serializable
data class RegisterResponse(
    val status: String,
    val message: String,
    val data: UserData? = null
)
