package com.android.post.presentation.posts

/**
 * To make an interaction between [PostsActivity] &
 * [PostsAdapter]
 *
 * @Author ZARA.
 * */
interface OnPostsActivityCallback {

    fun showPostDialog(postId: Int)
}