package com.abhishek.chitrashala.data

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.abhishek.chitrashala.data.database.ChitraShalaDB
import com.abhishek.chitrashala.data.database.PostEntity
import com.abhishek.chitrashala.data.network.Api
import com.abhishek.chitrashala.utils.Converters
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class PostsViewModel(app: Application) : AndroidViewModel(app) {

    private val dao = ChitraShalaDB.getInstance().postDataDao()
    private val subreddits = arrayOf("sketches", "PopArtNouveau, isometric")

    @SuppressLint("CheckResult")
    fun getRedditPosts(): LiveData<List<PostEntity>> {
        Single.fromCallable {
            dao.getCountOfPosts()
        }.subscribeOn(Schedulers.io())
            .subscribe({ count ->
                if (count == 0)
                    subreddits.forEach { getNewRedditPosts(it) }
            }, {})
        return dao.getPosts()
    }

    private fun getNewRedditPosts(subreddit: String) {
        Api.getRedditData(subreddit)
            .map { response ->
                val postEntities = response.body()?.let {
                    Converters.convertRedditDataToEntity(it)
                }
                postEntities?.let {
                    dao.insert(it)
                    return@map true
                }
                return@map false
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<Boolean> {
                override fun onSuccess(t: Boolean) {
                    Log.d("OOOOOOO", "Fetch new data success")
                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                    Log.e("OOOOOOO", e.toString())
                }
            })
    }
}