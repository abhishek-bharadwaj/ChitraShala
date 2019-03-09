package com.abhishek.chitrashala.ui

class PostUIModel(val imageUrl: String) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PostUIModel) return false

        if (imageUrl != other.imageUrl) return false

        return true
    }

    override fun hashCode(): Int {
        return imageUrl.hashCode()
    }
}