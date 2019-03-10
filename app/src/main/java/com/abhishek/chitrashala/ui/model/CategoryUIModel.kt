package com.abhishek.chitrashala.ui.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class CategoryUIModel(val id: String,
                      val displayName: String,
                      val activityUserCount: Int,
                      val allowImages: Boolean,
                      val bannerBgImage: String,
                      val bannerBgColor: String,
                      val description: String,
                      val primaryColor: String,
                      val createdAt: String,
                      val headerTitle: String,
                      val isOver18: Boolean,
                      val subscribers: Int,
                      val publicDescription: String,
                      val url: String,
                      val iconImage: String) : Parcelable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is CategoryUIModel) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}