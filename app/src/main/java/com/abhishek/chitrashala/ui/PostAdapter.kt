package com.abhishek.chitrashala.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abhishek.chitrashala.R
import com.abhishek.chitrashala.interfaces.PostClickCallbacks
import com.abhishek.chitrashala.utils.ImageLoader
import kotlinx.android.synthetic.main.layout_post_item.view.*

class PostAdapter(private val context: Context, val postClickCallbacks: PostClickCallbacks)
    : RecyclerView.Adapter<PostAdapter.PostVH>() {

    private val inflater = LayoutInflater.from(context)
    private val posts = mutableListOf<PostUIModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostVH {
        return PostVH(inflater.inflate(R.layout.layout_post_item, parent, false))
    }

    override fun getItemCount() = posts.size

    override fun onBindViewHolder(viewHolder: PostVH, position: Int) {
        val post = posts[position]
        ImageLoader.loadImage(context, post.imageUrl, viewHolder.itemView.iv_post)
        viewHolder.itemView.tag = post
    }

    fun updateData(posts: List<PostUIModel>) {
        this.posts.addAll(posts)
        notifyItemRangeInserted(this.posts.size, posts.size)
    }

    inner class PostVH(view: View) : RecyclerView.ViewHolder(view), View.OnLongClickListener {
        init {
            view.setOnLongClickListener(this)
        }

        override fun onLongClick(v: View?): Boolean {
            val postUIModel = v?.tag as PostUIModel? ?: return true
            postClickCallbacks.onPostLongClick(postUIModel)
            return true
        }
    }
}