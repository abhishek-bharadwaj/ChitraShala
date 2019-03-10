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
}
