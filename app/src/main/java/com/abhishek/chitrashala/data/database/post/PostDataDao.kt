package com.abhishek.chitrashala.data.database.post

import androidx.lifecycle.LiveData
import androidx.room.*
import com.abhishek.chitrashala.data.database.PostTable

@Dao
interface PostDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: PostEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: List<PostEntity>)

    @Update
    fun update(entity: PostEntity)

    @Query("SELECT * FROM ${PostTable.TABLE_NAME} order by ${PostTable.COLUMN_CREATED_AT} DESC")
    fun getPosts(): LiveData<List<PostEntity>>

    @Query("SELECT COUNT(${PostTable.COLUMN_IMAGE_URL}) FROM ${PostTable.TABLE_NAME}")
    fun getCountOfPosts(): Int

    @Query("DELETE from ${PostTable.TABLE_NAME}")
    fun deleteAll()
}