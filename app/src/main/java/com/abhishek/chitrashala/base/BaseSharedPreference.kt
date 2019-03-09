package com.abhishek.chitrashala.base

import android.content.SharedPreferences

abstract class BaseSharedPreference constructor(private val sharedPreferences: SharedPreferences) {

    fun getPref() = sharedPreferences

    fun getEditior() = sharedPreferences.edit()

    fun remove(key: String) {
        sharedPreferences.edit()?.remove(key)?.apply()
    }

    fun clear() {
        sharedPreferences.edit()?.clear()?.apply()
    }

    fun contains(key: String) = sharedPreferences.contains(key)
}