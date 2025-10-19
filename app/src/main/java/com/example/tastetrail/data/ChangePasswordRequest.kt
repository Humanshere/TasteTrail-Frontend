package com.example.tastetrail.data

import kotlinx.serialization.Serializable

@Serializable
data class ChangePasswordRequest(
    val current_password: String,
    val new_password: String
)
