package com.abhishek.chitrashala.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = DBConstants.TABLE_NAME)
class PostEntity(
    @PrimaryKey
    @ColumnInfo(name = DBConstants.COLUMN_ID) val id: String,
    @ColumnInfo(name = DBConstants.COLUMN_IMAGE_URL) val imageUrl: String,
    @ColumnInfo(name = DBConstants.COLUMN_TITLE) val title: String,
    @ColumnInfo(name = DBConstants.COLUMN_SUBREDDIT) val subreddit: String,
    @ColumnInfo(name = DBConstants.COLUMN_CREATED_AT) val createdAt: Long,
    @ColumnInfo(name = DBConstants.COLUMN_IS_STARRED) val isStarred: Boolean,
    @ColumnInfo(name = DBConstants.COLUMN_POST_LINK) val postLink: String,
    @ColumnInfo(name = DBConstants.COLUMN_LIKES) val likes: Int,
    @ColumnInfo(name = DBConstants.COLUMN_AUTHOR) val author: String,
    @ColumnInfo(name = DBConstants.COLUMN_IS_OVER_18) val isOver18: Boolean)