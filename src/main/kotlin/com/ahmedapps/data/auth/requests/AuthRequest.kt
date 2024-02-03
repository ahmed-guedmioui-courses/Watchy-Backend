package com.ahmedapps.data.auth.requests

import kotlinx.serialization.Serializable

@Serializable
data class AuthRequest(
    val name: String,
    val email: String,
    val password: String,
)