package com.ahmedapps.route

import com.ahmedapps.data.auth.requests.AuthRequest
import com.ahmedapps.data.auth.respounses.AuthResponse
import com.ahmedapps.data.user.User
import com.ahmedapps.data.user.UserDataSource
import com.ahmedapps.secutry.haching.HashingService
import com.ahmedapps.secutry.haching.SaltedHash
import com.ahmedapps.secutry.token.TokenClaim
import com.ahmedapps.secutry.token.TokenConfig
import com.ahmedapps.secutry.token.TokenService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
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
            return@post
        }

        val areFieldsBlank = request.name.isBlank() || request.password.isBlank()
        val isPwTooShort = request.password.length < 8
        if (areFieldsBlank || isPwTooShort) {
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
            call.respond(HttpStatusCode.Conflict)
            return@post
        }

        call.respond(HttpStatusCode.OK)
    }
}

fun Route.signin(
    hashingService: HashingService,
    userDataSource: UserDataSource,
    tokenService: TokenService,
    tokenConfig: TokenConfig
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

        val token = tokenService.generate(
            config = tokenConfig,
            TokenClaim(
                name = "userId",
                value = user.id.toString()
            )
        )

        call.respond(
            status = HttpStatusCode.OK,
            message = AuthResponse(
                name = user.name,
                token = token
            )
        )
    }

}


fun Route.authenticate() {
    authenticate {
        get("authenticate") {
            call.respond(HttpStatusCode.OK)
        }
    }
}

fun Route.getSecretInfo() {
    authenticate {
        get("secret") {
            val principal = call.principal<JWTPrincipal>()
            val userId = principal?.getClaim("userId", String::class)
            call.respond(HttpStatusCode.OK, "your userId is $userId")
        }
    }
}
















