package com.abhishek.chitrashala.data.database.category

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.abhishek.chitrashala.data.database.CategoryTable

@Entity(tableName = CategoryTable.TABLE_NAME)
class CategoryEntity(
    @PrimaryKey
    @ColumnInfo(name = CategoryTable.COLUMN_ID) val id: String,
    @ColumnInfo(name = CategoryTable.COLUMN_DISPLAY_NAME) val displayName: String,
    @ColumnInfo(name = CategoryTable.COLUMN_ACTIVE_USER_COUNT) val activityUserCount: Int,
    @ColumnInfo(name = CategoryTable.COLUMN_ALLOW_IMAGES) val allowImages: Boolean,
    @ColumnInfo(name = CategoryTable.COLUMN_BANNER_BACKGROUND_IMAGE) val bannerBgImage: String,
    @ColumnInfo(name = CategoryTable.COLUMN_BANNER_BACKGROUND_COLOR) val bannerBgColor: String,
    @ColumnInfo(name = CategoryTable.COLUMN_DESCRIPTION) val description: String,
    @ColumnInfo(name = CategoryTable.COLUMN_PRIMARY_COLOR) val primaryColor: String,
    @ColumnInfo(name = CategoryTable.COLUMN_CREATED_UTC) val createdAt: String,
    @ColumnInfo(name = CategoryTable.COLUMN_HEADER_TITLE) val headerTitle: String,
    @ColumnInfo(name = CategoryTable.COLUMN_IS_OVER_18) val isOver18: Boolean,
    @ColumnInfo(name = CategoryTable.COLUMN_SUBSCRIBERS) val subscribers: Int,
    @ColumnInfo(name = CategoryTable.COLUMN_PUBLIC_DESCRIPTION) val publicDescription: String,
    @ColumnInfo(name = CategoryTable.COLUMN_URL) val url: String,
    @ColumnInfo(name = CategoryTable.COLUMN_ICON_IMG) val iconImage: String
)