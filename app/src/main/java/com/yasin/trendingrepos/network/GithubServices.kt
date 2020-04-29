package com.yasin.trendingrepos.network

import com.yasin.trendingrepos.data.models.Owner
import com.yasin.trendingrepos.data.models.RepoSearchResult
import com.yasin.trendingrepos.data.models.Repository
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

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

    @GET
    fun getContributors(
        @Url url : String
    ): Call<List<Owner>>

    @GET
    fun getContributorRepos(
        @Url url : String
    ): Call<List<Repository>>
}