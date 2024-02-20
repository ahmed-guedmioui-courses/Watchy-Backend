package com.ahmedapps.route

import com.ahmedapps.data.auth.requests.AuthRequest
import com.ahmedapps.data.auth.respounses.AuthResponse
import com.ahmedapps.data.user.User
import com.ahmedapps.data.user.UserDataSource
import com.ahmedapps.secutry.haching.HashingService
import com.ahmedapps.secutry.haching.SaltedHash
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.signup(
    hashingService: HashingService,
    userDataSource: UserDataSource
) {
    post("signup") {
        val request = call.receiveNullable<AuthRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            println("Ahmed BadRequest")
            println("Ahmed BadRequest")
            println("Ahmed BadRequest")
            println("Ahmed BadRequest")
            return@post
        }

        val areFieldsBlank = request.name.isBlank() || request.password.isBlank()
        val isPwTooShort = request.password.length < 8
        if (areFieldsBlank || isPwTooShort) {
            println("Ahmed Conflict")
            println("Ahmed Conflict")
            println("Ahmed Conflict")
            println("Ahmed Conflict")
            call.respond(HttpStatusCode.Conflict, "Invalid password or username")
            return@post
        }

        val saltedHash = hashingService.generateSaltedHash(request.password)
        val user = User(
            name = request.name,
            email = request.email,
            password = saltedHash.hash,
            salt = saltedHash.salt,
            mediaList = ArrayList(),
        )
        val wasAcknowledge = userDataSource.insertUser(user)
        if (!wasAcknowledge) {
            println("Ahmed wasAcknowledge")
            println("Ahmed wasAcknowledge")
            println("Ahmed wasAcknowledge")
            println("Ahmed wasAcknowledge")
            call.respond(HttpStatusCode.Conflict)
            return@post
        }

        call.respond(HttpStatusCode.OK)
    }
}

fun Route.signin(
    hashingService: HashingService,
    userDataSource: UserDataSource
) {
    post("signin") {
        val request = call.receiveNullable<AuthRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }
        val user = userDataSource.getUserByEmail(request.email)
        if (user == null) {
            call.respond(HttpStatusCode.Conflict, "Incorrect email or password")
            return@post
        }

        val isValidPassword = hashingService.verify(
            value = request.password,
            saltedHash = SaltedHash(
                hash = user.password,
                salt = user.salt
            )
        )
        if (!isValidPassword) {
            call.respond(HttpStatusCode.Conflict, "Incorrect email or password")
            return@post
        }

        call.respond(
            status = HttpStatusCode.OK,
            message = AuthResponse(
                name = user.name
            )
        )
    }

}


fun Route.authenticate(userDataSource: UserDataSource) {
    post("authenticate") {
        val request = call.receiveNullable<AuthRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }
        val user = userDataSource.getUserByEmail(request.email)
        if (user == null) {
            call.respond(HttpStatusCode.Unauthorized, "Session expired")
            return@post
        }

        call.respond(
            status = HttpStatusCode.OK,
            message = AuthResponse(
                name = user.name
            )
        )
        call.respond(HttpStatusCode.OK, "authenticate succeeded")
    }
}

















