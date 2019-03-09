package com.abhishek.chitrashala.data

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.abhishek.chitrashala.data.database.ChitraShalaDB
import com.abhishek.chitrashala.data.database.PostEntity
import com.abhishek.chitrashala.data.network.Api
import com.abhishek.chitrashala.utils.Converters
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class PostsViewModel(app: Application) : AndroidViewModel(app) {

    private val dao = ChitraShalaDB.getInstance().postDataDao()

    fun getRedditPosts(): LiveData<List<PostEntity>> {
        val postsLiveData = dao.getPosts()
        if (postsLiveData.value?.size ?: 0 == 0) getNewPosts()
        return postsLiveData
    }

    private fun getNewPosts() {
        Api.getRedditData()
            .map { response ->
                val postEntities = response.body()?.let {
                    Converters.convertRedditDataToEntity(it)
                }.run {
                    emptyList<PostEntity>()
                }
                return@map if (postEntities.isNotEmpty()) {
                    dao.insert(postEntities)
                    true
                } else {
                    false
                }
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