package com.abhishek.chitrashala.data.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PostDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: PostEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: List<PostEntity>)

    @Update
    fun update(entity: PostEntity)

    @Query("SELECT * FROM ${DBConstants.TABLE_NAME} order by ${DBConstants.COLUMN_CREATED_AT} DESC")
    fun getPosts(): LiveData<List<PostEntity>>

    @Query("SELECT COUNT(${DBConstants.COLUMN_IMAGE_URL}) FROM ${DBConstants.TABLE_NAME}")
    fun getCountOfPosts(): Int

    @Query("DELETE from ${DBConstants.TABLE_NAME}")
    fun deleteAll()
}