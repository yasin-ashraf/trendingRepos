package com.yasin.trendingrepos.network

import com.yasin.trendingrepos.data.models.RepoSearchResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Yasin on 28/4/20.
 */
interface GithubServices {

    @GET("/search/repositories")
    fun getRepos(
        @Query("q") searchQuery: String,
        @Query("sort") sort: String,
        @Query("order") order: String,
        @Query("page") page: Int
    ): Call<RepoSearchResult>
}