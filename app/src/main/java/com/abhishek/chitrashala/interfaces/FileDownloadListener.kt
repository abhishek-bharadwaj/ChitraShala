package com.abhishek.chitrashala.interfaces

import java.io.File

interface FileDownloadListener {

    fun onSuccess(file: File)

    fun onError(t: Throwable)
}