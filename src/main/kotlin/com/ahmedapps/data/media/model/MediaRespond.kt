package com.ahmedapps.data.media.model

import kotlinx.serialization.Serializable

@Serializable
data class MediaRespond(
    val mediaId: Int,

    val isLiked: Boolean,
    val isBookmarked: Boolean,

    val adult: Boolean,
    val backdropPath: String,
    val firstAirDate: String,
    val genreIds: String,
    var mediaType: String,
    val originCountry: String,
    val originalLanguage: String,
    val originalName: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int,
    var category: String,

    var runtime: Int,
    var status: String,
    var tagline: String,

    var videosIds: String,
    var similarMediaList: String
)