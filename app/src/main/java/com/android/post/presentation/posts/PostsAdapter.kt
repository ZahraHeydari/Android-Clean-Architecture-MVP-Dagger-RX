package com.android.post.presentation.posts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.post.R
import com.android.post.data.model.Post
import kotlinx.android.synthetic.main.holder_post.view.*

class PostsAdapter(val callback: OnPostsActivityCallback) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TAG = PostsAdapter::class.java.name
    private val mPostList: MutableList<Post> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.holder_post, parent, false)
        return PostViewHolder(view)
    }

    override fun getItemCount(): Int {
        return if (mPostList.isNullOrEmpty()) 0 else mPostList.size
    }

    private fun getItem(position: Int): Post {
        return mPostList[position]
    }

    fun updateData(list: List<Post>) {
        mPostList.clear()
        mPostList.addAll(list)
        notifyDataSetChanged()
    }

    fun clearData() {
        mPostList.clear()
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as PostViewHolder).onBind(getItem(position))
    }

    inner class PostViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun onBind(post: Post) {
            itemView.post_title_text_view.text = post.title
            itemView.post_body_text_view.text = post.body

            itemView.setOnClickListener {
                callback.showPostDialog(post.id)
            }
        }
    }
}