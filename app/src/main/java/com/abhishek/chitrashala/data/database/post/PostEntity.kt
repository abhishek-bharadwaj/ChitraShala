package com.abhishek.chitrashala.data.database.post

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.abhishek.chitrashala.data.database.PostTable

@Entity(tableName = PostTable.TABLE_NAME)
class PostEntity(
    @PrimaryKey
    @ColumnInfo(name = PostTable.COLUMN_ID) val id: String,
    @ColumnInfo(name = PostTable.COLUMN_IMAGE_URL) val imageUrl: String,
    @ColumnInfo(name = PostTable.COLUMN_TITLE) val title: String,
    @ColumnInfo(name = PostTable.COLUMN_SUBREDDIT) val subreddit: String,
    @ColumnInfo(name = PostTable.COLUMN_CREATED_AT) val createdAt: Long,
    @ColumnInfo(name = PostTable.COLUMN_IS_STARRED) val isStarred: Boolean,
    @ColumnInfo(name = PostTable.COLUMN_POST_LINK) val postLink: String,
    @ColumnInfo(name = PostTable.COLUMN_LIKES) val likes: Int,
    @ColumnInfo(name = PostTable.COLUMN_AUTHOR) val author: String,
    @ColumnInfo(name = PostTable.COLUMN_IS_OVER_18) val isOver18: Boolean)