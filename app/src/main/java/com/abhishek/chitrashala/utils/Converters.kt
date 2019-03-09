package com.abhishek.chitrashala.utils

import com.abhishek.chitrashala.data.database.PostEntity
import com.abhishek.chitrashala.data.models.RedditData
import com.abhishek.chitrashala.ui.PostUIModel

object Converters {

    fun convertRedditDataToEntity(redditData: RedditData): List<PostEntity> {
        return redditData.postData.children.map { PostEntity(it.postInfo.url) }
    }

    fun convertPostEntityToUIModel(entity: PostEntity): PostUIModel {
        return PostUIModel(entity.imageUrl)
    }
}