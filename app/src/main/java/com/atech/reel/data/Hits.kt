package com.atech.reel.data

import androidx.annotation.Keep

@Keep
data class Hits(
    val id: Int,
    val pageURL: String,
    val type: String,
    val tags: String,
    val duration: Int,
    val picture_id: String,
    val videos: Videos,
    val views: Int,
    val downloads: Int,
    val likes: Int,
    val user: String,
    val userImageURL: String,
)

@Keep
data class Videos(
    val large: VideoType,
    val medium: VideoType,
    val small: VideoType,
    val tiny: VideoType,
)

@Keep
data class VideoType(
    val url: String,
    val width: Int,
    val height: Int,
    val size: Int,
)
