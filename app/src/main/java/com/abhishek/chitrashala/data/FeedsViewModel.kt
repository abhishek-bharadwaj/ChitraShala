package com.abhishek.chitrashala.data

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.abhishek.chitrashala.data.database.ChitraShalaDB
import com.abhishek.chitrashala.data.database.PostEntity
import com.abhishek.chitrashala.data.network.Api
import com.abhishek.chitrashala.interfaces.MessageReceiver
import com.abhishek.chitrashala.utils.Converters
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class FeedsViewModel(app: Application, private val messageReceiver: MessageReceiver) :
    AndroidViewModel(app) {

    private val dao = ChitraShalaDB.getInstance().postDataDao()
    private val subreddits = arrayListOf("sketches", "PopArtNouveau", "isometric")
    private var isLoadingNewPosts = false

    @SuppressLint("CheckResult")
    fun getRedditPosts(after: String?): LiveData<List<PostEntity>> {
        Single.fromCallable {
            dao.getCountOfPosts()
        }.subscribeOn(Schedulers.io())
            .subscribe({ count ->
                if (count == 0) {
                    getNewRedditPosts(subreddits.joinToString { "$it+" }, after)
                }
            }, {
                Timber.e(it.toString())
            })
        return dao.getPosts()
    }

    private fun getNewRedditPosts(subreddit: String, after: String?) {
        if (isLoadingNewPosts) return
        isLoadingNewPosts = true
        if (after.isNullOrEmpty()) {
            Api.getRedditData(subreddit)
        } else {
            Api.getRedditData(subreddit, after)
        }.map { response ->
            val postEntities = response.body()?.let {
                Converters.convertRedditDataToEntity(it)
            }
            postEntities?.let {
                dao.insert(it)
                return@map true
            }
            return@map false
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<Boolean> {
                override fun onSuccess(t: Boolean) {
                    isLoadingNewPosts = false
                    messageReceiver.onMessageReceived("Loading new posts..")
                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                    isLoadingNewPosts = false
                    Timber.e(e.toString())
                }
            })
    }
}