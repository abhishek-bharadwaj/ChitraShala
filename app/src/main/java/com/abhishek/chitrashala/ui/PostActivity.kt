package com.abhishek.chitrashala.ui

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.abhishek.chitrashala.R
import com.abhishek.chitrashala.base.BaseActivity
import com.abhishek.chitrashala.data.PostsViewModel
import com.abhishek.chitrashala.interfaces.MessageReceiver
import com.abhishek.chitrashala.interfaces.PostClickCallbacks
import com.abhishek.chitrashala.utils.*
import com.google.android.material.bottomsheet.BottomSheetBehavior
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_post.*


class PostActivity : BaseActivity(), PostClickCallbacks, View.OnClickListener, MessageReceiver {

    private lateinit var adapter: PostAdapter
    private lateinit var postViewModel: PostsViewModel
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>
    private val compositeDisposable = CompositeDisposable()

    private var clickedPost: PostUIModel? = null
    private var longClickedPost: PostUIModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

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
        setUpClickListeners()
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }

    override fun onPostClick(postUIModel: PostUIModel) {
        clickedPost = postUIModel
        bottomSheetBehavior.close()
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
    }

    private fun downloadFile() {
        val imageUrl = longClickedPost?.imageUrl ?: return
        ActionHelper.saveFileToDevice(this, imageUrl)
        bottomSheetBehavior.close()
    }
}
