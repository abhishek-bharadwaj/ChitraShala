package com.abhishek.chitrashala.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.abhishek.chitrashala.R
import com.abhishek.chitrashala.base.BaseActivity
import com.abhishek.chitrashala.utils.ImageLoader
import kotlinx.android.synthetic.main.activity_post.*

class PostActivity : BaseActivity() {

    companion object {

        private const val ARG_POST_UI_MODEL = "post_ui_model"

        fun startActivity(context: Context, postUIModel: PostUIModel) {
            context.startActivity(Intent(context, PostActivity::class.java)
                .apply {
                    putExtra(ARG_POST_UI_MODEL, postUIModel)
                })
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        val postData = intent.getParcelableExtra<PostUIModel>(ARG_POST_UI_MODEL)
        ImageLoader.loadImage(this, postData.imageUrl, iv_post)

        tv_title.text = postData.title
    }
}
