package com.abhishek.chitrashala.interfaces

import com.abhishek.chitrashala.ui.model.CategoryUIModel

interface CategoryClickCallbacks {

    fun onCategoryClick(categoryUIModel: CategoryUIModel)

    fun onCategoryLongClick(categoryUIModel: CategoryUIModel)
}