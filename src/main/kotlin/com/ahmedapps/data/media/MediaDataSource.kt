package com.ahmedapps.data.media

import com.ahmedapps.data.media.model.Media

interface MediaDataSource {
    suspend fun getMediaByIdAndUser(mediaId: Int, email: String): Media?
    suspend fun getLikedMediaListOfUser(email: String): List<Media>?
    suspend fun getBookmarkedMediaListOfUser(email: String): List<Media>?
    suspend fun upsertMediaToUser(media: Media, email: String): Boolean
    suspend fun deleteMediaFromUser(mediaId: Int, email: String): Boolean
}