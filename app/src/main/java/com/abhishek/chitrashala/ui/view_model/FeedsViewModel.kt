package com.abhishek.chitrashala.ui.view_model

import android.annotation.SuppressLint
import android.app.Application
import android.text.TextUtils
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.abhishek.chitrashala.ChitraShalaApp
import com.abhishek.chitrashala.data.database.ChitraShalaDB
import com.abhishek.chitrashala.data.database.post.PostEntity
import com.abhishek.chitrashala.data.network.Api
import com.abhishek.chitrashala.data.preferences.AppPreference
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
    private val pref = AppPreference()
    private var isLoadingNewPosts = false

    init {
        Timber.tag("FeedsViewModel")
    }

    @SuppressLint("CheckResult")
    fun getRedditPosts(after: String?): LiveData<List<PostEntity>> {
        Single.fromCallable {
            dao.getCountOfPosts()
        }.subscribeOn(Schedulers.io())
            .subscribe({ count ->
                Timber.d("Loading posts after %s", after)
                if (count > 0 && TextUtils.isEmpty(after)) return@subscribe
                getNewRedditPosts(ChitraShalaApp.subreddits.joinToString { "$it+" }, after)
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
            val redditData = response.body() ?: return@map false
            dao.insert(Converters.convertPostApiResponseToEntity(redditData))
            pref.savePostAfter(redditData.postData.after)
            return@map true
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