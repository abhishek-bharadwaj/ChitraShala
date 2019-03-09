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

    @Query("SELECT * FROM ${DBConstants.TABLE_NAME}")
    fun getPosts(): LiveData<List<PostEntity>>

    @Query("DELETE from ${DBConstants.TABLE_NAME}")
    fun deleteAll()
}