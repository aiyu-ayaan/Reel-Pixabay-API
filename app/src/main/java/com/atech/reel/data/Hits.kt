package com.atech.reel.data

import androidx.annotation.Keep
import androidx.recyclerview.widget.DiffUtil

data class PixaResponse(
    val total: Int,
    val totalHits: Int,
    val hits: List<Hits>,
)

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

class HitsDiffUtil : DiffUtil.ItemCallback<Hits>() {
    override fun areItemsTheSame(oldItem: Hits, newItem: Hits): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Hits, newItem: Hits): Boolean {
        return oldItem == newItem
    }
}