package com.abhishek.chitrashala.interfaces

import com.abhishek.chitrashala.ui.PostUIModel

interface PostClickCallbacks {

    fun onPostClick(postUIModel: PostUIModel)

    fun onPostLongClick(postUIModel: PostUIModel)
}