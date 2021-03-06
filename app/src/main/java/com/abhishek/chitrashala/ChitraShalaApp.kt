package com.abhishek.chitrashala

import android.app.Application
import timber.log.Timber
import timber.log.Timber.DebugTree


class ChitraShalaApp : Application() {

    companion object {
        lateinit var context: ChitraShalaApp
        val subreddits = arrayListOf("oldschoolcool", "PopArtNouveau", "isometric", "pic")
    }

    override fun onCreate() {
        super.onCreate()
        context = this

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
    }
}