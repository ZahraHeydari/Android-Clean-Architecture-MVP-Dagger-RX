package com.android.post.presentation.posts

import com.android.post.data.model.Post

/**
 * This specifies the contract between the view and the presenter.
 *
 * @Author ZARA.
 */
interface PostsContract {

    /**
     * To Update [PostsActivity] View
     */
    interface View {

        fun setResult(posts: List<Post>)

        fun showProgressBar(visibility: Boolean)

        fun showErrorMessage(errorMessage: String?)

        fun onClearView()
    }

    /**
     * To handle all user actions which is related to [PostsActivity]
     */
    interface UserActionsListener {

        fun start()

        fun onRefreshPage()

        fun onDestroy()

        fun onFilterData(text: CharSequence)
    }
}