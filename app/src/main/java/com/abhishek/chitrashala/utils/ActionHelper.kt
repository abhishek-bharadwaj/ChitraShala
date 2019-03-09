package com.abhishek.chitrashala.utils

import android.app.DownloadManager
import android.content.Context
import android.content.Context.DOWNLOAD_SERVICE
import android.net.Uri
import android.os.Environment
import com.abhishek.chitrashala.R
import timber.log.Timber


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
}