package com.abhishek.chitrashala.ui

import android.os.Bundle
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.abhishek.chitrashala.R
import com.abhishek.chitrashala.base.BaseActivity
import com.abhishek.chitrashala.data.PostsViewModel
import com.abhishek.chitrashala.interfaces.PostClickCallbacks
import com.abhishek.chitrashala.utils.Converters
import com.google.android.material.bottomsheet.BottomSheetBehavior
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*


class PostActivity : BaseActivity(), PostClickCallbacks {

    private lateinit var adapter: PostAdapter
    private lateinit var postViewModel: PostsViewModel
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = PostAdapter(this, this)
        adapter.setHasStableIds(true)
        rv_posts.layoutManager = LinearLayoutManager(this)
        rv_posts.adapter = adapter

        postViewModel = ViewModelProviders.of(this).get(PostsViewModel::class.java)
        postViewModel.getRedditPosts().observe(this, Observer { posts ->
            adapter.updateData(posts.map {
                Converters.convertPostEntityToUIModel(it)
            })
        })

        setUpBottomSheet()
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }

    override fun onPostLongClick(postUIModel: PostUIModel) {
        if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED) {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        } else {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }

    private fun setUpBottomSheet() {
//        bottomSheetBehavior = BottomSheetBehavior.from(ll_bottom_sheet)
//        bottomSheetBehavior.peekHeight = 0
    }
}
