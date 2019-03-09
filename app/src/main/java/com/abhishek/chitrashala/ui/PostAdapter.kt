package com.abhishek.chitrashala.ui

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.abhishek.chitrashala.R
import com.abhishek.chitrashala.data.models.Children
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.layout_post_item.view.*

class PostAdapter(private val context: Context) : RecyclerView.Adapter<PostAdapter.PostVH>() {

    private val inflater = LayoutInflater.from(context)
    private val posts = mutableListOf<Children>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostVH {
        return PostVH(inflater.inflate(R.layout.layout_post_item, parent, false))
    }

    override fun getItemCount() = posts.size

    override fun onBindViewHolder(viewHolder: PostVH, position: Int) {
        Glide.with(context)
            .load(posts[position].data.url)
            .into(viewHolder.itemView.iv_post)
        viewHolder.itemView.iv_post
    }

    fun updateData(posts: List<Children>) {
        this.posts.addAll(posts)
        notifyItemRangeInserted(this.posts.size, posts.size)
    }

    class PostVH(view: View) : RecyclerView.ViewHolder(view)
}