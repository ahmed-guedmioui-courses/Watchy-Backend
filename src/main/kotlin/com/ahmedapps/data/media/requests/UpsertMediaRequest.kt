package com.ahmedapps.data.media.requests

import kotlinx.serialization.Serializable

@Serializable
data class UpsertMediaRequest(
    val mediaRequest: MediaRequest,
    val email: String,
)