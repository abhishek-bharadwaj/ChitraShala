package com.abhishek.chitrashala.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions


object ImageLoader {

    fun loadImage(context: Context, imgUrl: String, iv: ImageView) {
        Glide.with(context)
            .load(imgUrl)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(iv)
    }
}