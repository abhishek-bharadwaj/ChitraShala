package com.abhishek.chitrashala.ui.activity

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abhishek.chitrashala.ChitraShalaApp
import com.abhishek.chitrashala.R
import com.abhishek.chitrashala.base.BaseActivity
import com.abhishek.chitrashala.data.preferences.AppPreference
import com.abhishek.chitrashala.interfaces.MessageReceiver
import com.abhishek.chitrashala.interfaces.PostClickCallbacks
import com.abhishek.chitrashala.ui.adapter.PostAdapter
import com.abhishek.chitrashala.ui.model.PostUIModel
import com.abhishek.chitrashala.ui.view_model.FeedsViewModel
import com.abhishek.chitrashala.ui.view_model.FeedsViewModelFactory
import com.abhishek.chitrashala.utils.*
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.activity_feed.*


class FeedActivity : BaseActivity(), PostClickCallbacks, View.OnClickListener, MessageReceiver {

    private lateinit var adapter: PostAdapter
    private lateinit var feedViewModel: FeedsViewModel
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>

    private var clickedPost: PostUIModel? = null
    private var longClickedPost: PostUIModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)

        adapter = PostAdapter(this, this)
        adapter.setHasStableIds(true)
        rv_posts.layoutManager = LinearLayoutManager(this)
        rv_posts.adapter = adapter
        rv_posts.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    feedViewModel.getRedditPosts(AppPreference().getPostAfter())
                }
            }
        })

        feedViewModel = ViewModelProviders.of(this,
            FeedsViewModelFactory(ChitraShalaApp.context, this))
            .get(FeedsViewModel::class.java)
        feedViewModel.getRedditPosts(null).observe(this, Observer { posts ->
            adapter.updateData(posts.map {
                Converters.convertPostEntityToUIModel(it)
            })
        })

        setUpBottomSheet()
        setUpClickListeners()
    }

    override fun onPostClick(postUIModel: PostUIModel) {
        clickedPost = postUIModel
        bottomSheetBehavior.close()
        PostActivity.startActivity(this, postUIModel)
    }

    override fun onPostLongClick(postUIModel: PostUIModel) {
        longClickedPost = postUIModel
        toggleBottomSheetState()
    }

    override fun onClick(v: View?) {
        when (v) {
            view_overlay -> {
                bottomSheetBehavior.close()
            }
            tv_download -> {
                if (hasWriteExternalStoragePermission(this)) {
                    downloadFile()
                }
            }
            tv_wallpaper -> {
                val imageUrl = longClickedPost?.imageUrl ?: return
                ActionHelper.setAsWallpaper(this, imageUrl, this)
                bottomSheetBehavior.close()
            }
            fab -> {
                ChooseCategoryActivity.startActivity(this)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<out String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            downloadFile()
        } else {
            showMessage(getString(R.string.please_give_permission))
        }
    }

    override fun onMessageReceived(message: String) {
        showMessage(message)
    }

    private fun setUpBottomSheet() {
        bottomSheetBehavior = BottomSheetBehavior.from(ll_bottom_sheet)
        bottomSheetBehavior.peekHeight = 0
        bottomSheetBehavior.setBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {

            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (bottomSheetBehavior.isOpen()) {
                    view_overlay.visible()
                    view_overlay.alpha = 0f
                    view_overlay.animate().alpha(1f).setDuration(300).start()
                } else {
                    view_overlay.alpha = 1f
                    view_overlay.animate().alpha(0f).setDuration(300).start()
                    view_overlay.gone()
                }
            }
        })
    }

    private fun toggleBottomSheetState() {
        if (bottomSheetBehavior.isOpen()) {
            bottomSheetBehavior.close()
        } else {
            bottomSheetBehavior.open()
        }
    }

    private fun setUpClickListeners() {
        view_overlay.setOnClickListener(this)
        tv_download.setOnClickListener(this)
        tv_fav.setOnClickListener(this)
        tv_wallpaper.setOnClickListener(this)
        fab.setOnClickListener(this)
    }

    private fun downloadFile() {
        val imageUrl = longClickedPost?.imageUrl ?: return
        ActionHelper.saveFileToDevice(this, imageUrl)
        bottomSheetBehavior.close()
    }
}
