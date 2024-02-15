package com.ahmedapps.route

import com.ahmedapps.data.media.MediaDataSource
import com.ahmedapps.data.media.mappers.toMedia
import com.ahmedapps.data.media.mappers.toMediaRespond
import com.ahmedapps.data.media.requests.MediaByIdRequest
import com.ahmedapps.data.media.requests.UpsertMediaRequest
import com.ahmedapps.data.user.UserDataSource
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getLikedMediaItemList(
    mediaDataSource: MediaDataSource,
    userDataSource: UserDataSource
) {
    route("get-liked-media-list/{email}") {

        get {

            val email = call.parameters["email"]
            if (email.isNullOrBlank()) {
                call.respond(HttpStatusCode.BadRequest, "Email must be specified")
                return@get
            }

            val user = userDataSource.getUserByEmail(email)
            if (user == null) {
                call.respond(HttpStatusCode.NotFound, "No such user")
                return@get
            }

            val likedMediaFromDb = mediaDataSource.getLikedMediaListOfUser(user.email)
            if (likedMediaFromDb == null) {
                call.respond(HttpStatusCode.NoContent, "No media for this user")
                return@get
            }

            val mediaRespond = likedMediaFromDb.map { it.toMediaRespond() }

            call.respond(
                status = HttpStatusCode.OK,
                message = mediaRespond
            )
        }
    }
}

fun Route.getBookmarkedMediaItemList(
    mediaDataSource: MediaDataSource,
    userDataSource: UserDataSource
) {
    route("get-bookmarked-media-list/{email}") {

        get {

            val email = call.parameters["email"]
            if (email.isNullOrBlank()) {
                call.respond(HttpStatusCode.BadRequest, "Email must be specified")
                return@get
            }

            val user = userDataSource.getUserByEmail(email)
            if (user == null) {
                call.respond(HttpStatusCode.NotFound, "No such user")
                return@get
            }

            val bookmarkedMediaFromDb = mediaDataSource.getBookmarkedMediaListOfUser(user.email)
            if (bookmarkedMediaFromDb == null) {
                call.respond(HttpStatusCode.NoContent, "No media for this user")
                return@get
            }

            val mediaRespond = bookmarkedMediaFromDb.map { it.toMediaRespond() }

            call.respond(
                status = HttpStatusCode.OK,
                message = mediaRespond
            )
        }
    }
}

fun Route.getMediaById(
    mediaDataSource: MediaDataSource,
    userDataSource: UserDataSource
) {
    route("get-media-by-id/{mediaId}/{email}") {

        get {

            val mediaIdStr = call.parameters["mediaId"]
            val email = call.parameters["email"]

            if (mediaIdStr.isNullOrBlank() || email.isNullOrBlank()) {
                call.respond(HttpStatusCode.BadRequest, "Media Id and email must be specified")
                return@get
            }

            val mediaId = mediaIdStr.toIntOrNull()
            if (mediaId == null) {
                call.respond(HttpStatusCode.BadRequest, "Invalid media Id format")
                return@get
            }

            val user = userDataSource.getUserByEmail(email)
            if (user == null) {
                call.respond(HttpStatusCode.NotFound, "No such user")
                return@get
            }

            val mediaFromDb = mediaDataSource.getMediaByIdAndUser(mediaId, email)

            if (mediaFromDb == null) {
                call.respond(HttpStatusCode.NoContent, "No such media")
                return@get
            }

            val mediaRespond = mediaFromDb.toMediaRespond()
            call.respond(
                status = HttpStatusCode.OK,
                message = mediaRespond
            )
        }
    }
}

fun Route.upsertMediaToUser(
    mediaDataSource: MediaDataSource,
    userDataSource: UserDataSource
) {
    post("upsert-media-to-user") {

        val request = call.receiveNullable<UpsertMediaRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest, "Invalid request")
            return@post
        }

        if (request.email.isBlank()) {
            call.respond(HttpStatusCode.BadRequest, "Email can't be blank")
            return@post
        }

        val user = userDataSource.getUserByEmail(request.email)
        if (user == null) {
            call.respond(HttpStatusCode.NotFound, "No such user")
            return@post
        }

        val media = request.mediaRequest.toMedia()

        val wasAcknowledged = mediaDataSource.upsertMediaToUser(media, request.email)

        if (!wasAcknowledged) {
            call.respond(HttpStatusCode.Conflict, "Media upsert failed")
            return@post
        }

        call.respond(
            status = HttpStatusCode.OK,
            message = media.mediaId.toString()
        )
    }
}

fun Route.deleteMediaFromUser(
    mediaDataSource: MediaDataSource,
    userDataSource: UserDataSource
) {
    post("delete-media-from-user") {

        val request = call.receiveNullable<MediaByIdRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest, "Invalid request")
            return@post
        }

        if (request.email.isBlank()) {
            call.respond(HttpStatusCode.BadRequest, "Email can't be blank")
            return@post
        }

        val user = userDataSource.getUserByEmail(request.email)
        if (user == null) {
            call.respond(HttpStatusCode.NotFound, "No such user")
            return@post
        }

        val wasAcknowledged = mediaDataSource.deleteMediaFromUser(request.mediaId, request.email)

        if (!wasAcknowledged) {
            call.respond(HttpStatusCode.NotFound, "Media deletion failed or media not found")
            return@post
        }


        call.respond(
            status = HttpStatusCode.OK,
            message = "Media deletion succeeded"
        )
    }
}











