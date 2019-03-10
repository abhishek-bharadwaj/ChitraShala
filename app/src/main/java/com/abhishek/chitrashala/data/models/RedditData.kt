package com.abhishek.chitrashala.data.models

import com.google.gson.annotations.SerializedName

class RedditData(@SerializedName("data") val postData: Data) {

    class Data(@SerializedName("children") val children: List<Children>,
               @SerializedName("after") val after: String,
               @SerializedName("before") val before: String) {

        class Children(@SerializedName("data") val postInfo: PostInfo) {

            class PostInfo(
                val id: String,
                val author: String,
                val author_fullname: String,
                val category: Any,
                val created_utc: Long,
                val is_video: Boolean,
                val ups: Int,
                val name: String,
                val over_18: Boolean,
                val permalink: String,
                val score: Int,
                val subreddit: String,
                val subreddit_id: String,
                val subreddit_name_prefixed: String,
                val subreddit_subscribers: Int,
                val thumbnail: String,
                val thumbnail_height: Int,
                val thumbnail_width: Int,
                val title: String,
                val url: String,
                val view_count: Any
            )
        }
    }
}