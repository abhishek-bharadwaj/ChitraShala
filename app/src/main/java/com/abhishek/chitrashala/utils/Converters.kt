package com.abhishek.chitrashala.utils

import com.abhishek.chitrashala.data.database.category.CategoryEntity
import com.abhishek.chitrashala.data.database.post.PostEntity
import com.abhishek.chitrashala.data.models.AboutApiResponse
import com.abhishek.chitrashala.data.models.PostApiResponse
import com.abhishek.chitrashala.ui.PostUIModel
import com.abhishek.chitrashala.ui.adapter.CategoryUIModel

object Converters {

    fun convertPostApiResponseToEntity(postApiResponse: PostApiResponse): List<PostEntity> {
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

    fun convertCategoryApiResponseToEntity(response: AboutApiResponse): CategoryEntity {
        val subData = response.subData
        return CategoryEntity(
            id = subData.id,
            displayName = subData.displayName,
            activeUserCount = subData.activeUserCount,
            allowImages = subData.allowImages,
            bannerBgImage = subData.bannerBgImage,
            bannerBgColor = subData.bannerBgColor,
            description = subData.description,
            primaryColor = subData.primaryColor,
            createdAt = subData.createdAt,
            headerTitle = subData.title,
            isOver18 = subData.isOver18,
            subscribers = subData.subscribers,
            publicDescription = subData.publicDescription,
            url = subData.url,
            iconImage = subData.iconImage
        )
    }

    fun convertCategoryEntityToUIModel(entity: CategoryEntity): CategoryUIModel {
        return CategoryUIModel(
            id = entity.id,
            displayName = entity.displayName,
            activityUserCount = entity.activeUserCount,
            allowImages = entity.allowImages,
            bannerBgImage = entity.bannerBgImage,
            bannerBgColor = entity.bannerBgColor,
            description = entity.description,
            primaryColor = entity.primaryColor,
            createdAt = entity.createdAt,
            headerTitle = entity.headerTitle,
            isOver18 = entity.isOver18,
            subscribers = entity.subscribers,
            publicDescription = entity.publicDescription,
            url = entity.url,
            iconImage = entity.iconImage
        )
    }
}