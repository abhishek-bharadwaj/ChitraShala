package com.abhishek.chitrashala.data.database

import android.annotation.SuppressLint
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.abhishek.chitrashala.ChitraShalaApp
import com.abhishek.chitrashala.data.database.post.PostDataDao
import com.abhishek.chitrashala.data.database.post.PostEntity
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers

@Database(entities = [PostEntity::class], version = 1)
abstract class ChitraShalaDB : RoomDatabase() {

    abstract fun postDataDao(): PostDataDao

    private object HOLDER {
        val INSTANCE = Room.databaseBuilder(
            ChitraShalaApp.context, ChitraShalaDB::class.java, DATABASE_NAME)
            .build()
    }

    companion object {

        private val INSTANCE: ChitraShalaDB by lazy { HOLDER.INSTANCE }

        @Synchronized
        fun getInstance() = INSTANCE

        @SuppressLint("CheckResult")
        fun clearDatabase() {
            Completable.fromCallable {
                getInstance().clearAllTables()
            }.subscribeOn(Schedulers.io())
                .subscribe()
        }
    }
}