package com.ahmedapps.plugins

import com.ahmedapps.data.media.MediaDataSource
import com.ahmedapps.data.user.UserDataSource
import com.ahmedapps.route.*
import com.ahmedapps.secutry.haching.HashingService
import com.ahmedapps.secutry.token.TokenConfig
import com.ahmedapps.secutry.token.TokenService
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting(
    hashingService: HashingService,
    userDataSource: UserDataSource,
    mediaDataSource: MediaDataSource,
    tokenService: TokenService,
    tokenConfig: TokenConfig
) {
    routing {

        signin(
            hashingService, userDataSource, tokenService, tokenConfig
        )
        signup(hashingService, userDataSource)
        authenticate()
        getSecretInfo()

        getLikedMediaItemList(mediaDataSource, userDataSource)
        getBookmarkedMediaItemList(mediaDataSource, userDataSource)
        getMediaById(mediaDataSource, userDataSource)
        upsertMediaToUser(mediaDataSource, userDataSource)
        deleteMediaFromUser(mediaDataSource, userDataSource)
    }
}
