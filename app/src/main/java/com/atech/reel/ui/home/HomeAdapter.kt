package com.atech.reel.ui.home

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout.RESIZE_MODE_FILL
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.atech.reel.R
import com.atech.reel.data.Hits
import com.atech.reel.data.HitsDiffUtil
import com.atech.reel.databinding.RowVideoBinding
import com.atech.reel.utils.formatNumber
import com.atech.reel.utils.loadImageCircular

@UnstableApi
class HomeAdapter : PagingDataAdapter<Hits, HomeAdapter.HomeViewHolder>(HitsDiffUtil()) {


    private val players = mutableListOf<ExoPlayer>()

    inner class HomeViewHolder(
        private val binding: RowVideoBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        private var isPlaying = true
        private var player: ExoPlayer? = null

        init {
            binding.videoView.setOnClickListener {
                isPlaying = if (isPlaying) {
                    player?.pause()
                    binding.imageViewPlay.isVisible = true
                    false
                } else {
                    binding.imageViewPlay.isVisible = false
                    player?.play()
                    true
                }
            }
        }

        fun bind(hits: Hits) {
            binding.apply {
                textViewLikes.text = hits.likes.formatNumber()
                textViewViews.text = hits.views.formatNumber()
                textViewUser.text = hits.user
                textViewTags.text = hits.tags
                hits.userImageURL.loadImageCircular(
                    binding.root, imageViewUser, R.drawable.ic_user
                )
                setUpExoPlayer(hits.videos.tiny.url)
                binding.buttonFollow.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(hits.pageURL)
                    binding.root.context.startActivity(intent)
                }
            }
        }

        private fun setUpExoPlayer(videoUrl: String) {
            player = ExoPlayer.Builder(binding.root.context)
                .build()
                .also { exoPlayer ->
                    binding.videoView.player = exoPlayer
                    val mediaItem = MediaItem.fromUri(videoUrl)
                    binding.videoView.resizeMode = RESIZE_MODE_FILL
                    binding.videoView.useController = false
                    exoPlayer.setMediaItem(mediaItem)
                    exoPlayer.playWhenReady = true
                    exoPlayer.prepare()
                    players.add(exoPlayer)
                }
        }

        fun releasePlayer() {
            player?.release()
            players.remove(player)
            player = null
        }
    }


    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder =
        HomeViewHolder(
            RowVideoBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onViewRecycled(holder: HomeViewHolder) {
        super.onViewRecycled(holder)
        holder.releasePlayer()
    }
}