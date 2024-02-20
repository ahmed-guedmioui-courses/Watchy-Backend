package com.ahmedapps.plugins

import com.ahmedapps.data.media.MediaDataSource
import com.ahmedapps.data.user.UserDataSource
import com.ahmedapps.route.*
import com.ahmedapps.secutry.haching.HashingService
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting(
    hashingService: HashingService,
    userDataSource: UserDataSource,
    mediaDataSource: MediaDataSource
) {
    routing {

        signin(
            hashingService, userDataSource
        )
        signup(hashingService, userDataSource)
        authenticate(userDataSource)
        getLikedMediaItemList(mediaDataSource, userDataSource)
        getBookmarkedMediaItemList(mediaDataSource, userDataSource)
        getMediaById(mediaDataSource, userDataSource)
        upsertMediaToUser(mediaDataSource, userDataSource)
        deleteMediaFromUser(mediaDataSource, userDataSource)
    }
}
