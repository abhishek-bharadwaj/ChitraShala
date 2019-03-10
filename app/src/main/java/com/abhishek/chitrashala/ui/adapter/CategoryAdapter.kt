package com.abhishek.chitrashala.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abhishek.chitrashala.R
import com.abhishek.chitrashala.interfaces.CategoryClickCallbacks
import com.abhishek.chitrashala.ui.model.CategoryUIModel
import com.abhishek.chitrashala.utils.ImageLoader
import kotlinx.android.synthetic.main.layout_category_item.view.*

class CategoryAdapter(private val context: Context,
                      private val categoryClickCallbacks: CategoryClickCallbacks)
    : RecyclerView.Adapter<CategoryAdapter.CategoryVH>() {

    private val inflater = LayoutInflater.from(context)
    private val categories = mutableListOf<CategoryUIModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryVH {
        return CategoryVH(inflater.inflate(R.layout.layout_category_item, parent, false))
    }

    override fun getItemCount() = categories.size

    override fun onBindViewHolder(holder: CategoryVH, position: Int) {
        val category = categories[position]
        holder.itemView.tv_name.text = category.displayName
        ImageLoader.loadImage(context, category.bannerBgImage, holder.itemView.iv_banner)
    }

    fun updateData(categories: List<CategoryUIModel>) {
        this.categories.addAll(categories)
        notifyItemRangeInserted(this.categories.size, categories.size)
    }

    inner class CategoryVH(view: View) : RecyclerView.ViewHolder(view),
        View.OnClickListener, View.OnLongClickListener {
        init {
            view.setOnClickListener(this)
            view.setOnLongClickListener(this)
        }

        override fun onClick(v: View?) {
            val postUIModel = v?.getTag(R.id.tag_url) as CategoryUIModel? ?: return
            categoryClickCallbacks.onCategoryClick(postUIModel)
        }

        override fun onLongClick(v: View?): Boolean {
            val postUIModel = v?.getTag(R.id.tag_url) as CategoryUIModel? ?: return true
            categoryClickCallbacks.onCategoryLongClick(postUIModel)
            return true
        }
    }
}