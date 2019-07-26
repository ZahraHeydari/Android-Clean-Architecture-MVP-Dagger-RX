package com.android.post.domain.repository

import com.android.post.data.model.Post
import io.reactivex.Single

/**
 * This class is used to make an interaction between sub classes of [SingleUseCase]
 * such as [GetPostsUseCase]
 *
 * @Author ZARA.
 * */
interface PostsRepository {

    fun getPosts(): Single<List<Post>>
}