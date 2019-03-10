package com.abhishek.chitrashala.data.preferences

import android.content.Context
import com.abhishek.chitrashala.ChitraShalaApp
import com.abhishek.chitrashala.base.BaseSharedPreference
import java.util.*

class AppPreference : BaseSharedPreference(ChitraShalaApp.context.getSharedPreferences(
    "app_pref", Context.MODE_PRIVATE)) {

    companion object {
        private const val KEY_SUBSCRIBED_SET = "subscribed_set"
        private const val KEY_POST_AFTER = "post_after"
    }

    fun saveSubscribedSubreddits(subscribed: LinkedHashSet<String>) {
        getEditior().putStringSet(KEY_SUBSCRIBED_SET, subscribed).apply()
    }

    fun getSubscribedSubreddits() {
        getPref().getStringSet(KEY_SUBSCRIBED_SET, emptySet())
    }

    fun savePostAfter(postAfter: String) = getEditior().putString(KEY_POST_AFTER, postAfter).apply()

    fun getPostAfter() = getPref().getString(KEY_POST_AFTER, null)
}