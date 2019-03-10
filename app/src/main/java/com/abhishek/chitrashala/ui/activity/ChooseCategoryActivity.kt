package com.abhishek.chitrashala.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.abhishek.chitrashala.ChitraShalaApp
import com.abhishek.chitrashala.R
import com.abhishek.chitrashala.base.BaseActivity
import com.abhishek.chitrashala.interfaces.MessageReceiver
import com.abhishek.chitrashala.ui.view_model.CategoriesViewModel
import com.abhishek.chitrashala.ui.view_model.ViewModelFactory

class ChooseCategoryActivity : BaseActivity(), MessageReceiver {

    private lateinit var categoriesViewModel: CategoriesViewModel

    companion object {
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, ChooseCategoryActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_category)

        categoriesViewModel = ViewModelProviders.of(this,
            ViewModelFactory(ChitraShalaApp.context, this))
            .get(CategoriesViewModel::class.java)
    }

    override fun onMessageReceived(message: String) {
        showMessage(message)
    }
}
