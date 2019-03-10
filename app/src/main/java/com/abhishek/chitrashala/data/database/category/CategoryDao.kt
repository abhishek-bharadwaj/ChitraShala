package com.abhishek.chitrashala.data.database.category

import androidx.lifecycle.LiveData
import androidx.room.*
import com.abhishek.chitrashala.data.database.CategoryTable

@Dao
interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: CategoryEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: List<CategoryEntity>)

    @Update
    fun update(entity: CategoryEntity)

    @Query("SELECT * FROM ${CategoryTable.TABLE_NAME} order by ${CategoryTable.COLUMN_DISPLAY_NAME} DESC")
    fun getPosts(): LiveData<List<CategoryEntity>>

    @Query("SELECT COUNT(${CategoryTable.COLUMN_ID}) FROM ${CategoryTable.TABLE_NAME}")
    fun getCountOfPosts(): Int

    @Query("DELETE from ${CategoryTable.TABLE_NAME}")
    fun deleteAll()
}