package com.android.post.di.builder

import com.android.post.data.source.remote.ApiErrorHandle
import com.android.post.domain.repository.PostsRepository
import com.android.post.domain.usecase.GetPostsUseCase
import com.android.post.presentation.posts.PostsActivity
import com.android.post.presentation.posts.PostsContract
import com.android.post.presentation.posts.PostsPresenter
import dagger.Module
import dagger.Provides

@Module
class PostsPresentersBuilder {

    @Provides
    fun providePostsPresenter(
        postsView: PostsContract.View,
        getPostsUseCase: GetPostsUseCase
    ): PostsContract.UserActionsListener {
        return PostsPresenter(postsView, getPostsUseCase)
    }

    @Provides
    fun providePostsView(postsActivity: PostsActivity): PostsContract.View {
        return postsActivity
    }

    @Provides
    fun provideGetPostsUseCase(
        postsRepository: PostsRepository,
        apiErrorHandle: ApiErrorHandle
    ): GetPostsUseCase {
        return GetPostsUseCase(postsRepository, apiErrorHandle)
    }
}