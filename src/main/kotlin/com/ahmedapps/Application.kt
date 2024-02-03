package com.ahmedapps

import com.ahmedapps.data.media.MongoMediaDataSource
import com.ahmedapps.data.media.model.Media
import com.ahmedapps.data.user.MongoUserDataSource
import com.ahmedapps.data.user.User
import com.ahmedapps.plugins.configureMonitoring
import com.ahmedapps.plugins.configureRouting
import com.ahmedapps.plugins.configureSecurity
import com.ahmedapps.plugins.configureSerialization
import com.ahmedapps.secutry.haching.SHA256HashingService
import com.ahmedapps.secutry.token.JwtTokenService
import com.ahmedapps.secutry.token.TokenConfig
import io.ktor.server.application.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo
import java.util.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {

    val mongoPassword = System.getenv("MONGO_PASSWORD")
    val databaseName = "the-movies-info-db"

    val db = KMongo.createClient(
        connectionString = "mongodb+srv://theMoviesInfoUser:$mongoPassword@the-movies-info-cluster.ujtutlt.mongodb.net/$databaseName?retryWrites=true&w=majority"
    ).coroutine
        .getDatabase(databaseName)


    val userDataSource = MongoUserDataSource(db)
    val mediaDataSource = MongoMediaDataSource(db)
    val tokenService = JwtTokenService()
    val tokenConfig = TokenConfig(
        issuer = environment.config.property("jwt.issuer").getString(),
        audience = environment.config.property("jwt.audience").getString(),
        expiresIn = 365L * 1000L * 60L * 60L * 24L,
        secret = System.getenv("JWT_SECRET")
    )
    val hashingService = SHA256HashingService()

    configureSerialization()
    configureMonitoring()
    configureSecurity(tokenConfig)
    configureRouting(hashingService, userDataSource, mediaDataSource, tokenService, tokenConfig)


}

fun test(
    userDataSource: MongoUserDataSource,
    mediaDataSource: MongoMediaDataSource
) {

    val dummyMediaList = ArrayList<Media>()
    dummyMediaList.add(
        Media(
            mediaId = 1,
            isLiked = true,
            isBookmarked = false,
            adult = false,
            backdropPath = "/backdrop.jpg",
            firstAirDate = "2022-01-26",
            genreIds = "1,2,3",
            mediaType = "movie",
            originCountry = "US",
            originalLanguage = "en",
            originalName = "Original Name",
            originalTitle = "Original Title",
            overview = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
            popularity = 7.8,
            posterPath = "/poster.jpg",
            releaseDate = "2022-01-26",
            title = "Title",
            video = false,
            voteAverage = 8.0,
            voteCount = 100,
            category = "Action",
            runtime = 120,
            status = "Released",
            tagline = "Awesome Movie",
            videosIds = "video1,video2",
            similarMediaList = "media1,media2"
        )
    )

    dummyMediaList.add(
        Media(
            mediaId = 2,
            isLiked = false,
            isBookmarked = true,
            adult = true,
            backdropPath = "/another_backdrop.jpg",
            firstAirDate = "2022-02-10",
            genreIds = "4,5,6",
            mediaType = "tv",
            originCountry = "UK",
            originalLanguage = "en",
            originalName = "Another Original Name",
            originalTitle = "Another Original Title",
            overview = "Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium.",
            popularity = 6.5,
            posterPath = "/another_poster.jpg",
            releaseDate = "2022-02-10",
            title = "Another Title",
            video = true,
            voteAverage = 7.5,
            voteCount = 80,
            category = "Drama",
            runtime = 150,
            status = "In Production",
            tagline = "Exciting TV Series",
            videosIds = "video3,video4",
            similarMediaList = "media3,media4"
        )
    )

    val user = User(
        name = "test name",
        email = "test@gmail.com",
        password = "test password",
        salt = "test salt",
        mediaList = dummyMediaList
    )

    GlobalScope.launch {
        println("insertUser")
        println("insertUser")
        println("insertUser")
       val insert = userDataSource.insertUser(user)
        println("insertUser: $insert")
        println("insertUser: $insert")
        println("insertUser: $insert")
    }

//    GlobalScope.launch {
//        mediaDataSource.updateMediaOfUser(medias[0], "ahmed.1@gmail.com")
//    }

//    GlobalScope.launch {
//        mediaDataSource.insertMediaToUser(mediaList[1], "ahmed.1@gmail.com")
//    }

}








