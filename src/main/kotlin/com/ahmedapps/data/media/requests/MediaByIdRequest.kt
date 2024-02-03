package com.ahmedapps.data.media.requests

import kotlinx.serialization.Serializable

@Serializable
data class MediaByIdRequest(
    val mediaId: Int,
    val email: String,
)