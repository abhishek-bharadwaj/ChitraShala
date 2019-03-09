package com.abhishek.chitrashala.utils

import android.view.View
import com.google.android.material.bottomsheet.BottomSheetBehavior

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun BottomSheetBehavior<*>.open() {
    this.state = com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED
}

fun BottomSheetBehavior<*>.close() {
    this.state = com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_COLLAPSED
}

fun BottomSheetBehavior<*>.isOpen() =
    this.state == com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED
