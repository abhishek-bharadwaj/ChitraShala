package com.abhishek.chitrashala.ui.view_model

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.abhishek.chitrashala.ChitraShalaApp
import com.abhishek.chitrashala.data.database.ChitraShalaDB
import com.abhishek.chitrashala.data.database.category.CategoryEntity
import com.abhishek.chitrashala.data.network.Api
import com.abhishek.chitrashala.interfaces.MessageReceiver
import com.abhishek.chitrashala.utils.Converters
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class CategoriesViewModel(app: Application, private val messageReceiver: MessageReceiver) :
    AndroidViewModel(app) {

    private val dao = ChitraShalaDB.getInstance().getCategoryDao()

    init {
        Timber.tag("CategoriesViewModel")
    }

    @SuppressLint("CheckResult")
    fun getCategoriesData(): LiveData<List<CategoryEntity>> {
        Single.fromCallable {
            dao.getCountOfCategories()
        }.subscribeOn(Schedulers.io())
            .subscribe({ count ->
                Timber.d("Count of category is %s", count)
                dao.deleteAll()
                if (count > 0) return@subscribe
                getNewSubredditCategories(ChitraShalaApp.subreddits)
            }, {
                Timber.e(it.toString())
            })
        return dao.getCategories()
    }

    private fun getNewSubredditCategories(subreddits: ArrayList<String>) {
        subreddits.forEach { subreddit ->
            Api.getSubredditInfo(subreddit)
                .map { response ->
                    val aboutApiResponse = response.body() ?: return@map false
                    dao.insert(Converters.convertCategoryApiResponseToEntity(aboutApiResponse))
                    Timber.d("Saved %s to DB", aboutApiResponse.subData.displayName)
                    return@map true
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<Boolean> {
                    override fun onSuccess(t: Boolean) {
                        messageReceiver.onMessageReceived("Fetching new categories..")
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onError(e: Throwable) {
                        Timber.e(e.toString())
                    }
                })
        }
    }
}