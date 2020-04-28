package com.yasin.trendingrepos.ui

import androidx.lifecycle.LiveData
import com.yasin.trendingrepos.models.RepoSearchResult
import com.yasin.trendingrepos.network.GithubServices
import com.yasin.trendingrepos.network.NetworkBoundResource
import com.yasin.trendingrepos.network.NetworkState
import retrofit2.Call
import java.util.concurrent.Executor
import javax.inject.Inject

/**
 * Created by Yasin on 28/4/20.
 */
class ReposRepository @Inject constructor(
    private val githubServices: GithubServices,
    private val executor: Executor
) {

    fun getReposForSearchQuery(searchQuery : String) : LiveData<NetworkState<RepoSearchResult>> {

        return object : NetworkBoundResource<RepoSearchResult,RepoSearchResult>() {

            override fun saveCallResult(item: RepoSearchResult?) {

            }

            override fun shouldFetch(data: RepoSearchResult?): Boolean {
                return true
            }

            override fun loadFromDb(): LiveData<RepoSearchResult> {
                TODO("Not yet implemented")
            }

            override fun createCall(): Call<RepoSearchResult> {
                return githubServices.getRepos(searchQuery,"stars","desc", 1)
            }

            override fun onFetchFailed() {

            }

        }.asLiveData
    }
}