package com.abhishek.chitrashala.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.abhishek.chitrashala.ChitraShalaApp
import com.abhishek.chitrashala.R
import com.abhishek.chitrashala.base.BaseActivity
import com.abhishek.chitrashala.interfaces.CategoryClickCallbacks
import com.abhishek.chitrashala.interfaces.MessageReceiver
import com.abhishek.chitrashala.ui.adapter.CategoryAdapter
import com.abhishek.chitrashala.ui.model.CategoryUIModel
import com.abhishek.chitrashala.ui.view_model.CategoriesViewModel
import com.abhishek.chitrashala.ui.view_model.CategoryViewModelFactory
import com.abhishek.chitrashala.utils.Converters
import kotlinx.android.synthetic.main.activity_choose_category.*

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

        categoryAdapter = CategoryAdapter(this, this)
        categoryAdapter.setHasStableIds(true)
        rv_categories.layoutManager = LinearLayoutManager(this)
        rv_categories.adapter = categoryAdapter

        categoriesViewModel = ViewModelProviders.of(this,
            CategoryViewModelFactory(ChitraShalaApp.context, this))
            .get(CategoriesViewModel::class.java)
        categoriesViewModel.getCategoriesData().observe(this, Observer { categories ->
            categoryAdapter.updateData(categories.map {
                Converters.convertCategoryEntityToUIModel(it)
            })
        })
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