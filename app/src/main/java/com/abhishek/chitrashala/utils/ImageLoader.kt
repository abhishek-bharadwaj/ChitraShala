package com.abhishek.chitrashala.utils

import android.content.Context
import android.widget.ImageView
import com.abhishek.chitrashala.interfaces.FileDownloadListener
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.io.File


object ImageLoader {

    fun loadImage(context: Context, imgUrl: String, iv: ImageView) {
        Glide.with(context)
            .load(imgUrl)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(iv)
    }

    fun downloadImage(context: Context, imageUrl: String, downloadListener: FileDownloadListener) {
        Single.fromCallable {
            Glide.with(context)
                .asFile()
                .load(imageUrl)
                .submit()
                .get()
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<File> {
                override fun onSuccess(t: File) {
                    downloadListener.onSuccess(t)
                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {
                    downloadListener.onError(e)
                }
            })
    }
}