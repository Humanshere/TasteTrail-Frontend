package com.example.tastetrail.data

import kotlinx.serialization.Serializable

@Serializable
data class ChangePasswordResponse(
    val success: Boolean,
    val message: String
)
