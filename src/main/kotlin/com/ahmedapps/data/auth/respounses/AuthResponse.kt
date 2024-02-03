package com.ahmedapps.data.auth.respounses

import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val name: String,
    val token: String
)
