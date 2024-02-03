package com.ahmedapps.data.media.mappers

import com.ahmedapps.data.media.model.Media
import com.ahmedapps.data.media.model.MediaRespond
import com.ahmedapps.data.media.requests.MediaRequest


fun Media.toMediaRespond(): MediaRespond {
    return MediaRespond(
        mediaId = mediaId,

        isLiked = isLiked,
        isBookmarked = isBookmarked,

        backdropPath = backdropPath,
        originalLanguage = originalLanguage,
        overview = overview,
        posterPath = posterPath,
        releaseDate = releaseDate,
        title = title,
        voteAverage = voteAverage,
        popularity = popularity,
        voteCount = voteCount,
        genreIds = genreIds,
        adult = adult,
        mediaType = mediaType,
        originCountry = originCountry,
        originalTitle = originalTitle,
        category = category,
        video = false,
        firstAirDate = releaseDate,
        originalName = originalTitle,

        status = status,
        runtime = runtime,
        tagline = tagline,

        videosIds = videosIds,
        similarMediaList = similarMediaList
    )
}

fun MediaRespond.toMedia(): Media {
    return Media(
        mediaId = mediaId,

        isLiked = isLiked,
        isBookmarked = isBookmarked,

        backdropPath = backdropPath,
        originalLanguage = originalLanguage,
        overview = overview,
        posterPath = posterPath,
        releaseDate = releaseDate,
        title = title,
        voteAverage = voteAverage,
        popularity = popularity,
        voteCount = voteCount,
        genreIds = genreIds,
        adult = adult,
        mediaType = mediaType,
        originCountry = originCountry,
        originalTitle = originalTitle,
        category = category,
        video = false,
        firstAirDate = releaseDate,
        originalName = originalTitle,

        status = status,
        runtime = runtime,
        tagline = tagline,

        videosIds = videosIds,
        similarMediaList = similarMediaList
    )
}

fun MediaRequest.toMedia(): Media {
    return Media(
        mediaId = mediaId,

        isLiked = isLiked,
        isBookmarked = isBookmarked,

        backdropPath = backdropPath,
        originalLanguage = originalLanguage,
        overview = overview,
        posterPath = posterPath,
        releaseDate = releaseDate,
        title = title,
        voteAverage = voteAverage,
        popularity = popularity,
        voteCount = voteCount,
        genreIds = genreIds,
        adult = adult,
        mediaType = mediaType,
        originCountry = originCountry,
        originalTitle = originalTitle,
        category = category,
        video = false,
        firstAirDate = releaseDate,
        originalName = originalTitle,

        status = status,
        runtime = runtime,
        tagline = tagline,

        videosIds = videosIds,
        similarMediaList = similarMediaList
    )
}







