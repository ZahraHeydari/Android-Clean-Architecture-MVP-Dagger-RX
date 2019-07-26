package com.android.post.presentation.posts

import android.util.Log
import com.android.post.data.model.ErrorModel
import com.android.post.data.model.Post
import com.android.post.domain.usecase.GetPostsUseCase
import com.android.post.domain.usecase.base.UseCaseResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * This presenter listens to user actions from [PostsActivity], execute [GetPostsUseCase] &
 * retrieves the data and updates the
 * UI as required through [PostsContract.View]
 *
 * @Author ZARA.
 * */
class PostsPresenter @Inject constructor(
    val postsView: PostsContract.View,
    private val getPostsUseCase: GetPostsUseCase
) : PostsContract.UserActionsListener {

    private val TAG = PostsPresenter::class.java.name
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val subject = PublishSubject.create<String>()
    private var subscribe: Disposable? = null
    private var posts: List<Post>? = null
    private val FILTER_TIME_OUT = 200L


    override fun start() {
        initFilter()
        getAllPosts()
    }

    fun initFilter() {
        subscribe = subject.debounce(FILTER_TIME_OUT, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { s -> applyFilter(s) }
    }

    override fun onFilterData(text: CharSequence) {
        postsView.showProgressBar(true)
        subject.onNext(text.toString())
    }

    override fun onRefreshPage() {
        getAllPosts()
    }

    fun getAllPosts() {
        postsView.showProgressBar(true)
        getPostsUseCase.execute(compositeDisposable, null, object : UseCaseResponse<List<Post>> {
            override fun onSuccess(value: List<Post>) {
                Log.i(TAG, "getPostsUseCase: $value")
                posts = value
                checkResult(value)
                postsView.showProgressBar(false)
            }

            override fun onError(error: Throwable, errorModel: ErrorModel?) {
                error.printStackTrace()
                postsView.showProgressBar(false)
                postsView.showErrorMessage(errorModel?.getErrorMessage())
            }
        })
    }

    private fun checkResult(posts: List<Post>?) {
        if (posts.isNullOrEmpty()) {
            postsView.onClearView()
            return
        }
        postsView.setResult(posts)
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        if (subscribe?.isDisposed == true) subscribe?.dispose()
    }

    private fun applyFilter(word: String) {
        val filterList = posts?.filter { it.title.contains(word) }
        checkResult(filterList)
        postsView.showProgressBar(false)
    }
}