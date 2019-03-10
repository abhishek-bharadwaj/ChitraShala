package com.abhishek.chitrashala.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.abhishek.chitrashala.ChitraShalaApp
import com.abhishek.chitrashala.R
import com.abhishek.chitrashala.base.BaseActivity
import com.abhishek.chitrashala.interfaces.CategoryClickCallbacks
import com.abhishek.chitrashala.interfaces.MessageReceiver
import com.abhishek.chitrashala.ui.adapter.CategoryAdapter
import com.abhishek.chitrashala.ui.model.CategoryUIModel
import com.abhishek.chitrashala.ui.view_model.CategoriesViewModel
import com.abhishek.chitrashala.ui.view_model.ViewModelFactory

class ChooseCategoryActivity : BaseActivity(), MessageReceiver, CategoryClickCallbacks {

    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var categoriesViewModel: CategoriesViewModel

    private var clickedCategory: CategoryUIModel? = null
    private var longClickedCategory: CategoryUIModel? = null

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
        categoryAdapter = CategoryAdapter(this, this)
    }

    override fun onCategoryClick(categoryUIModel: CategoryUIModel) {
        clickedCategory = categoryUIModel
    }

    override fun onCategoryLongClick(categoryUIModel: CategoryUIModel) {
        longClickedCategory = categoryUIModel
    }

    override fun onMessageReceived(message: String) {
        showMessage(message)
    }
}