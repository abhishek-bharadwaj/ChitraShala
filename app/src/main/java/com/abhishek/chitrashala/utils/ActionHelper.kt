package com.abhishek.chitrashala.utils

import android.app.DownloadManager
import android.app.WallpaperManager
import android.content.Context
import android.content.Context.DOWNLOAD_SERVICE
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import com.abhishek.chitrashala.ChitraShalaApp
import com.abhishek.chitrashala.R
import com.abhishek.chitrashala.interfaces.BitmapDownloadListener
import com.abhishek.chitrashala.interfaces.MessageReceiver
import timber.log.Timber
import java.io.IOException


object ActionHelper {

    init {
        Timber.tag("ActionHelper")
    }

    fun saveFileToDevice(context: Context, imageUrl: String) {
        if (Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED) {
            Timber.e("Storage is not mounted")
        }
        val request = DownloadManager.Request(Uri.parse(imageUrl))
        val appName = context.getString(R.string.app_name)
        request.setTitle(appName)
        request.setDescription(context.getString(R.string.downloading_wait))
        val fileName = "${appName.replace(" ", "")}-${System.currentTimeMillis()}.jpg"
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.allowScanningByMediaScanner()
        val manager = context.getSystemService(DOWNLOAD_SERVICE) as DownloadManager?
        manager?.enqueue(request)
    }

    fun setAsWallpaper(context: Context, imageUrl: String, messageReceiver: MessageReceiver) {
        val myWallpaperManager = WallpaperManager.getInstance(ChitraShalaApp.context)
        ImageLoader.downloadImage(context, imageUrl, object : BitmapDownloadListener {

            override fun onSuccess(bitmap: Bitmap) {
                try {
                    myWallpaperManager.setBitmap(bitmap)
                    messageReceiver.onMessageReceived(context.getString(R.string.wallpaper_set))
                } catch (e: IOException) {
                    Timber.e(e)
                    messageReceiver.onMessageReceived(context.getString(R.string.something_wrong_try_again))
                }
            }

            override fun onError(t: Throwable) {
                Timber.e(t)
                messageReceiver.onMessageReceived(context.getString(R.string.something_wrong_try_again))
            }
        })
    }
}