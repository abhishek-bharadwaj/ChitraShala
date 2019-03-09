package com.abhishek.chitrashala.ui

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.abhishek.chitrashala.R
import com.abhishek.chitrashala.base.BaseActivity
import com.abhishek.chitrashala.data.PostsViewModel
import com.abhishek.chitrashala.utils.Converters
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*

class PostActivity : BaseActivity() {

    private lateinit var adapter: PostAdapter
    private lateinit var postViewModel: PostsViewModel
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = PostAdapter(this)
        adapter.setHasStableIds(true)
        rv_posts.layoutManager = LinearLayoutManager(this)
        rv_posts.adapter = adapter

        postViewModel = ViewModelProviders.of(this).get(PostsViewModel::class.java)
        postViewModel.getRedditPosts().observe(this, Observer { posts ->
            adapter.updateData(posts.map {
                Converters.convertPostEntityToUIModel(it)
            })
        })
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }
}
