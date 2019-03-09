package com.abhishek.chitrashala.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abhishek.chitrashala.R
import com.abhishek.chitrashala.data.models.Children
import com.abhishek.chitrashala.utils.ImageLoader
import kotlinx.android.synthetic.main.layout_post_item.view.*

class PostAdapter(private val context: Context) : RecyclerView.Adapter<PostAdapter.PostVH>() {

    private val inflater = LayoutInflater.from(context)
    private val posts = mutableListOf<Children>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostVH {
        return PostVH(inflater.inflate(R.layout.layout_post_item, parent, false))
    }

    override fun getItemCount() = posts.size

    override fun onBindViewHolder(viewHolder: PostVH, position: Int) {
        ImageLoader.loadImage(context, posts[position].data.url, viewHolder.itemView.iv_post)
        viewHolder.itemView.iv_post
    }

    fun updateData(posts: List<Children>) {
        this.posts.addAll(posts)
        notifyItemRangeInserted(this.posts.size, posts.size)
    }

    class PostVH(view: View) : RecyclerView.ViewHolder(view)
}