package com.abhishek.chitrashala.data

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.abhishek.chitrashala.data.database.ChitraShalaDB
import com.abhishek.chitrashala.data.database.PostEntity
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
    private val subreddits = arrayListOf("sketches", "PopArtNouveau", "isometric")
    private val pref = AppPreference()
    private var isLoadingNewPosts = false

    @SuppressLint("CheckResult")
    fun getRedditPosts(): LiveData<List<PostEntity>> {
        val after = AppPreference().getPostAfter()
        Single.fromCallable {
            dao.getCountOfPosts()
        }.subscribeOn(Schedulers.io())
            .subscribe({ count ->
                if (count > 0 && after.isNullOrEmpty()) return@subscribe
                getNewRedditPosts(subreddits.joinToString { "$it+" }, after)
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
            dao.insert(Converters.convertRedditDataToEntity(redditData))
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