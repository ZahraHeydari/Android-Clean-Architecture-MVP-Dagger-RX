package com.android.post.presentation.posts

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import com.android.post.R
import com.android.post.data.model.Post
import com.android.post.presentation.post.PostDialogFragment
import com.android.post.util.NetworkStateReceiver
import com.android.post.util.hideKeyboard
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_posts.*
import javax.inject.Inject

class PostsActivity : DaggerAppCompatActivity(), OnPostsActivityCallback, PostsContract.View,
    NetworkStateReceiver.OnNetworkStateReceiverListener {

    private val TAG = PostsActivity::class.java.name
    private var mAdapter: PostsAdapter? = null
    @Inject
    lateinit var mPresenter: PostsPresenter
    @Inject
    lateinit var mNetworkReceiver: NetworkStateReceiver


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_posts)

        mNetworkReceiver.addListener(this)
        registerReceiver(mNetworkReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

        mAdapter = PostsAdapter(this)
        posts_recycler_view?.adapter = mAdapter

        posts_swipe_refresh_layout?.setOnRefreshListener {
            hideKeyboard()
            posts_filter_edit_text?.text?.clear()
            mPresenter.onRefreshPage()
            posts_swipe_refresh_layout?.isRefreshing = false
        }

        posts_filter_edit_text?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                //nothing to do here
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //nothing to do here
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                mPresenter.onFilterData(p0 ?: "")
            }

        })
        mPresenter.start()
    }

    override fun showErrorMessage(errorMessage: String?) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
    }

    override fun showPostDialog(postId: Int) {
        PostDialogFragment.newInstance(postId).show(supportFragmentManager, PostDialogFragment.FRAGMENT_NAME)
    }

    override fun showProgressBar(visibility: Boolean) {
        posts_progress_bar?.visibility = if (visibility) View.VISIBLE else View.GONE
    }

    override fun setResult(posts: List<Post>) {
        Log.i(TAG, "List size: ${posts.size}")
        mAdapter?.updateData(posts)
    }

    override fun onClearView() {
        mAdapter?.clearData()
    }

    override fun networkAvailable() {
        mPresenter.onRefreshPage()
    }

    override fun networkUnavailable() {
        showErrorMessage(getString(R.string.no_internet_connection_available))
    }

    override fun onDestroy() {
        super.onDestroy()
        mAdapter = null
        mPresenter.onDestroy()

        mNetworkReceiver.removeListener(this)
        unregisterReceiver(mNetworkReceiver)//to avoid memory leak
    }
}
