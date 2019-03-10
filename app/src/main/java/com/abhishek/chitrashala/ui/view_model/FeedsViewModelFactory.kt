package com.abhishek.chitrashala.ui.view_model

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.abhishek.chitrashala.interfaces.MessageReceiver

class FeedsViewModelFactory(private val application: Application,
                            private val messageReceiver: MessageReceiver) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FeedsViewModel(application, messageReceiver) as T
    }
}