package com.abhishek.chitrashala.ui

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class PostUIModel(val imageUrl: String,
                  val subreddit: String,
                  val createdAt: Long,
                  val isStarred: Boolean,
                  val author: String,
                  val likes: Int,
                  val postLink: String,
                  val title: String,
                  val isOver18: Boolean) : Parcelable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PostUIModel) return false

        if (imageUrl != other.imageUrl) return false

        return true
    }

    override fun hashCode(): Int {
        return imageUrl.hashCode()
    }
}