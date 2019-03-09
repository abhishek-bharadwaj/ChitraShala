package com.abhishek.chitrashala.data.preferences

import android.content.Context
import com.abhishek.chitrashala.ChitraShalaApp
import com.abhishek.chitrashala.base.BaseSharedPreference
import java.util.*

class AppPreference : BaseSharedPreference(ChitraShalaApp.context.getSharedPreferences(
    "app_pref", Context.MODE_PRIVATE)) {

    companion object {
        const val SUBSCRIBED_SET = "subscribed_set"
    }

    fun saveSubscribedSubreddits(subscribed: LinkedHashSet<String>) {
        getEditior().putStringSet(SUBSCRIBED_SET, subscribed).apply()
    }

    fun getSubscribedSubreddits() {
        getPref().getStringSet(SUBSCRIBED_SET, emptySet())
    }
}