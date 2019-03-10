package com.abhishek.chitrashala.data.database

const val DATABASE_NAME = "chitrashala.db"

object PostTable {
    const val TABLE_NAME = "posts"

    const val COLUMN_ID = "id"
    const val COLUMN_IMAGE_URL = "image_url"
    const val COLUMN_SUBREDDIT = "subreddit"
    const val COLUMN_CREATED_AT = "created_at"
    const val COLUMN_IS_STARRED = "is_starred"
    const val COLUMN_POST_LINK = "post_link"
    const val COLUMN_TITLE = "title"
    const val COLUMN_LIKES = "likes"
    const val COLUMN_AUTHOR = "author"
    const val COLUMN_IS_OVER_18 = "is_over_18"
}

object CategoryTable {
    const val TABLE_NAME = "categories"

    const val COLUMN_ID = "id"
    const val COLUMN_PUBLIC_DESCRIPTION = "public_description"
    const val COLUMN_BANNER_BACKGROUND_IMAGE = "banner_background_image"
    const val COLUMN_BANNER_BACKGROUND_COLOR = "banner_background_color"
    const val COLUMN_PRIMARY_COLOR = "primary_color"
    const val COLUMN_HEADER_TITLE = "header_title"
    const val COLUMN_DESCRIPTION = "description"
    const val COLUMN_IS_OVER_18 = "is_over_18"
    const val COLUMN_DISPLAY_NAME = "display_name"
    const val COLUMN_ACTIVE_USER_COUNT = "active_user_count"
    const val COLUMN_ICON_IMG = "icon_img"
    const val COLUMN_SUBSCRIBERS = "subscribers"
    const val COLUMN_CREATED_UTC = "created_utc"
    const val COLUMN_URL = "url"
    const val COLUMN_ALLOW_IMAGES = "allow_images"
}
