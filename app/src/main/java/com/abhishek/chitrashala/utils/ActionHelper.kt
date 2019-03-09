package com.abhishek.chitrashala.utils

import android.content.Context
import com.abhishek.chitrashala.interfaces.FileDownloadListener
import timber.log.Timber
import java.io.File

object ActionHelper {

    init {
        Timber.tag("ActionHelper")
    }

    fun saveFileToDevice(context: Context, imageUrl: String) {
        ImageLoader.downloadImage(context, imageUrl, object : FileDownloadListener {
            override fun onSuccess(file: File) {
                Timber.d("File downloaded. ${file.absolutePath}")
            }

            override fun onError(t: Throwable) {
                Timber.e(t)
            }
        })
    }
}