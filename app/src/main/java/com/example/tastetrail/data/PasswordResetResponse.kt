package com.example.tastetrail.data

import kotlinx.serialization.Serializable

@Serializable
data class PasswordResetResponse(
    val status: String,
    val message: String
)
