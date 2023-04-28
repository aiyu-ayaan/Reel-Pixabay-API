package com.atech.reel.utils

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions


private const val TAG = "ExtensionFunctions"

fun String.loadImageCircular(
    parentView: View, view: ImageView, @DrawableRes errorImage: Int
) = try {
    Glide.with(parentView).load(this).centerCrop().apply(RequestOptions().circleCrop())
        .error(errorImage).transition(DrawableTransitionOptions.withCrossFade())
        .listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: com.bumptech.glide.request.target.Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: com.bumptech.glide.request.target.Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                return false
            }

        }).timeout(10000).into(view)
} catch (e: Exception) {
    Log.d(TAG, "loadImageCircular: ${e.message}")
}

fun Int.formatNumber(): String {
    return when {
        this >= 1_000_000_000 -> String.format("%.1fb", this / 1_000_000_000f)
        this >= 1_000_000 -> String.format("%.1fm", this / 1_000_000f)
        this >= 1_000 -> String.format("%.1fk", this / 1_000f)
        else -> this.toString()
    }
}
