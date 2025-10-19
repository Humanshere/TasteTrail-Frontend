package com.example.tastetrail.data

import kotlinx.serialization.Serializable

@Serializable
data class ConfirmPasswordResetRequest(
    val token: String,
    val new_password: String
)
