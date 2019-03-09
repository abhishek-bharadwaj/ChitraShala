package com.abhishek.chitrashala.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = DBConstants.TABLE_NAME)
class PostEntity(@PrimaryKey @ColumnInfo(name = DBConstants.COLUMN_IMAGE_URL) var imageUrl: String)