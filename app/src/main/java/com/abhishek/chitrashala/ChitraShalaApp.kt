package com.abhishek.chitrashala

import android.app.Application

class ChitraShalaApp : Application() {

    companion object {
        lateinit var context: ChitraShalaApp
    }

    override fun onCreate() {
        super.onCreate()
        context = this
    }
}