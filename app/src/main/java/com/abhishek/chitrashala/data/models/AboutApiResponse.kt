package com.abhishek.chitrashala.data.models

import com.google.gson.annotations.SerializedName

class AboutApiResponse(@SerializedName("data") val subData: SubData) {

    class SubData(
        @SerializedName("id") val id: String,
        @SerializedName("public_description") val publicDescription: String,
        @SerializedName("banner_background_image") val bannerBgImage: String,
        @SerializedName("banner_background_color") val bannerBgColor: String,
        @SerializedName("primary_color") val primaryColor: String,
        @SerializedName("header_title") val title: String,
        @SerializedName("description") val description: String,
        @SerializedName("over18") val isOver18: Boolean,
        @SerializedName("display_name") val displayName: String,
        @SerializedName("active_user_count") val activeUserCount: Int,
        @SerializedName("icon_img") val iconImage: String,
        @SerializedName("subscribers") val subscribers: Int,
        @SerializedName("created_utc") val createdAt: String,
        @SerializedName("url") val url: String,
        @SerializedName("allow_images") val allowImages: Boolean)
}