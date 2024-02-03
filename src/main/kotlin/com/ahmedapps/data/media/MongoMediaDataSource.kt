package com.ahmedapps.data.media

import com.ahmedapps.data.media.model.Media
import com.ahmedapps.data.user.User
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import org.litote.kmongo.setValue

class MongoMediaDataSource(
    db: CoroutineDatabase
) : MediaDataSource {

    private val users = db.getCollection<User>()


    override suspend fun getMediaByIdAndUser(mediaId: Int, email: String): Media? {
        return users.findOne(User::email eq email)?.mediaList?.find {
            it.mediaId == mediaId
        }
    }

    override suspend fun getLikedMediaListOfUser(email: String): List<Media>? {
        return users.findOne(User::email eq email)?.mediaList?.filter { it.isLiked }
    }

    override suspend fun getBookmarkedMediaListOfUser(email: String): List<Media>? {
        return users.findOne(User::email eq email)?.mediaList?.filter { it.isBookmarked }
    }

    override suspend fun upsertMediaToUser(media: Media, email: String): Boolean {
        val user = users.findOne(User::email eq email) ?: return false

        // Find and update the existing media or add the new media to the list
        val updatedMediaList = user.mediaList.map {
            if (it.mediaId == media.mediaId) media else it
        }.toMutableList()

        // If the media is not found, add it to the list
        if (!updatedMediaList.contains(media)) {
            updatedMediaList.add(media)
        }

        val updateResult = users.updateOne(
            User::email eq email,
            setValue(User::mediaList, updatedMediaList)
        )

        return updateResult.wasAcknowledged()
    }


    override suspend fun deleteMediaFromUser(mediaId: Int, email: String): Boolean {
        val user = users.findOne(User::email eq email) ?: return false

        // Find the mediaList with the specified mediaId in the user's medias
        val mediaToRemove = user.mediaList.find { it.mediaId == mediaId }

        if (mediaToRemove != null) {
            user.mediaList.remove(mediaToRemove)
        } else {
            return false
        }

        val deletionsResult = users.updateOne(
            User::email eq email,
            setValue(User::mediaList, user.mediaList)
        )

        return deletionsResult.wasAcknowledged()
    }
}











