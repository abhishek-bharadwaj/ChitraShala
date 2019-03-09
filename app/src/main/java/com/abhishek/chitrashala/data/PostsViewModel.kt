package com.abhishek.chitrashala.data

import android.annotation.SuppressLint
import android.app.Application
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
import timber.log.Timber

class PostsViewModel(app: Application) : AndroidViewModel(app) {

    private val dao = ChitraShalaDB.getInstance().postDataDao()
    private val subreddits = arrayListOf("sketches", "PopArtNouveau", "isometric")

    @SuppressLint("CheckResult")
    fun getRedditPosts(): LiveData<List<PostEntity>> {
        Single.fromCallable {
            dao.getCountOfPosts()
        }.subscribeOn(Schedulers.io())
            .subscribe({ count ->
                if (count == 0) {
                    getNewRedditPosts(subreddits.joinToString { "$it+" })
                }
            }, {
                Timber.e(it.toString())
            })
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
                    Timber.d("Fetch new data success")
                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                    Timber.e(e.toString())
                }
            })
    }
}