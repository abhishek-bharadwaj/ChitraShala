package com.abhishek.chitrashala.data.preferences

import android.content.Context
import com.abhishek.chitrashala.ChitraShalaApp
import com.abhishek.chitrashala.base.BaseSharedPreference

class AppPreference : BaseSharedPreference(ChitraShalaApp.context.getSharedPreferences(
    "app_pref", Context.MODE_PRIVATE)) {

    companion object {
        private const val KEY_POST_AFTER = "post_after"
    }

    fun savePostAfter(postAfter: String) = getEditior().putString(KEY_POST_AFTER, postAfter).apply()

    fun getPostAfter(): String? = getPref().getString(KEY_POST_AFTER, null)
}