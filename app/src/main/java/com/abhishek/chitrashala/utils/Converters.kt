package com.abhishek.chitrashala.utils

import com.abhishek.chitrashala.data.database.post.PostEntity
import com.abhishek.chitrashala.data.models.PostApiResponse
import com.abhishek.chitrashala.ui.PostUIModel

object Converters {

    fun convertRedditDataToEntity(postApiResponse: PostApiResponse): List<PostEntity> {
        return postApiResponse.postData.children.map {
            val postInfo = it.postInfo
            PostEntity(
                id = postInfo.id,
                imageUrl = postInfo.url,
                subreddit = postInfo.subreddit,
                createdAt = postInfo.created_utc,
                isStarred = false,
                author = postInfo.author,
                likes = postInfo.ups,
                postLink = postInfo.permalink,
                title = postInfo.title,
                isOver18 = postInfo.over_18
            )
        }
    }

    fun convertPostEntityToUIModel(entity: PostEntity): PostUIModel {
        return PostUIModel(
            id = entity.id,
            imageUrl = entity.imageUrl,
            subreddit = entity.subreddit,
            createdAt = entity.createdAt,
            isStarred = entity.isStarred,
            author = entity.author,
            likes = entity.likes,
            postLink = entity.postLink,
            title = entity.title,
            isOver18 = entity.isOver18
        )
    }
}