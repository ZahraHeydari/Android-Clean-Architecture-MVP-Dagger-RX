package com.android.post.data.repository

import com.android.post.data.model.Post
import com.android.post.data.source.remote.ApiService
import com.android.post.domain.repository.PostsRepository
import io.reactivex.Single

/**
 * This class designed to handle data operations. It provides a clean API so that the rest of the app can retrieve this data easily.
 * It knows where to get the data from and what API calls to make when data is updated.
 * You can consider repositories to be mediators between different data sources, such as persistent models,
 * web services, and caches. Otherwise, this repository returns data from a data source (Cache or Remote.)
 *
 * @Author ZARA.
 */
class PostsRepositoryImp(private val apiService: ApiService) : PostsRepository {

    override fun getPosts(): Single<List<Post>> {
        return apiService.getPosts()
    }
}