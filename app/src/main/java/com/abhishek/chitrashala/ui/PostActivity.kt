package com.abhishek.chitrashala.ui

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.abhishek.chitrashala.R
import com.abhishek.chitrashala.base.BaseActivity
import com.abhishek.chitrashala.data.Api
import com.abhishek.chitrashala.data.models.RedditData
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Response

class PostActivity : BaseActivity() {

    private lateinit var adapter: PostAdapter
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = PostAdapter(this)
        rv_posts.layoutManager = LinearLayoutManager(this)
        rv_posts.adapter = adapter

        Api.getRedditData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<Response<RedditData>> {
                override fun onSuccess(t: Response<RedditData>) {
                    if (t.isSuccessful && t.body() != null)
                        adapter.updateData(t.body()?.data?.children ?: emptyList())
                }

                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onError(e: Throwable) {

                }
            })
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }
}
