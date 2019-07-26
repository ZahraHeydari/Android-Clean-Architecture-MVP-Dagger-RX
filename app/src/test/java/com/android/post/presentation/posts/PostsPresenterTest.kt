package com.android.post.presentation.posts

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PostsPresenterTest {


    @Mock
    lateinit var mPostsPresenter: PostsPresenter//mock object
    lateinit var view: PostsContract.View

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this) //for initialization
        mPostsPresenter = Mockito.mock(PostsPresenter::class.java)
        view = mock()
    }

    @Test
    fun start() {
        mPostsPresenter.initFilter()
        verify(mPostsPresenter).initFilter()

        mPostsPresenter.getAllPosts()
        verify(mPostsPresenter).getAllPosts()
    }

    @Test
    fun filter_createSubscribe() {
        mPostsPresenter.initFilter()
        verify(mPostsPresenter).initFilter()
    }

    @Test
    fun posts_refreshPosts() {
        mPostsPresenter.onRefreshPage()
        verify(mPostsPresenter).onRefreshPage()
    }

    @Test
    fun filter_withNextText_showProgressBar() {
        mPostsPresenter.onFilterData("qui")
        view.showProgressBar(true)
        verify(view).showProgressBar(true)
    }

    @Test
    fun filter_withEmptyText() {
        mPostsPresenter.applyFilter("")
        verify(mPostsPresenter).applyFilter(any())
    }

    @Test
    fun filter_withText_callsSetResult() {
        mPostsPresenter.applyFilter("est")
        view.setResult(ArgumentMatchers.anyList())
        verify(view).setResult(ArgumentMatchers.anyList())
    }


}