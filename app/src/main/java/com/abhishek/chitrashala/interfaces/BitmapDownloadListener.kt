package com.abhishek.chitrashala.interfaces

import android.graphics.Bitmap

interface BitmapDownloadListener {

    fun onSuccess(bitmap: Bitmap)

    fun onError(t: Throwable)
}