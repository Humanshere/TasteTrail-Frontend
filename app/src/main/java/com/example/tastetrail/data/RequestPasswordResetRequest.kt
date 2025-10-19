package com.example.tastetrail.data

import kotlinx.serialization.Serializable

@Serializable
data class RequestPasswordResetRequest(
    val email: String
)
