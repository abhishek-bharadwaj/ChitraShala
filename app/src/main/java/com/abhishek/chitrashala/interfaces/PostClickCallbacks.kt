package com.abhishek.chitrashala.interfaces

import com.abhishek.chitrashala.ui.model.PostUIModel

interface PostClickCallbacks {

    fun onPostClick(postUIModel: PostUIModel)

    fun onPostLongClick(postUIModel: PostUIModel)
}