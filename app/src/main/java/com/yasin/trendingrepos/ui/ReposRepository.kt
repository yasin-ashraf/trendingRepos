package com.yasin.trendingrepos.ui

import androidx.lifecycle.LiveData
import com.yasin.trendingrepos.data.dataBase.dao.ReposSearchDao
import com.yasin.trendingrepos.data.dataBase.entity.SearchResultDb
import com.yasin.trendingrepos.data.models.RepoSearchResult
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
    private val executor: Executor,
    private val reposSearchDao: ReposSearchDao
) {

    fun getReposForSearchQuery(searchQuery : String) : LiveData<NetworkState<SearchResultDb>> {

        return object : NetworkBoundResource<SearchResultDb, RepoSearchResult>() {

            override fun saveCallResult(item: RepoSearchResult?) {

            }

            override fun shouldFetch(data: SearchResultDb?): Boolean {
                return true
            }

            override fun loadFromDb(): LiveData<SearchResultDb> {
                return reposSearchDao.getSearchResult()
            }

            override fun createCall(): Call<RepoSearchResult> {
                return githubServices.getRepos(searchQuery,"stars","desc", 1)
            }

            override fun onFetchFailed() {

            }

        }.asLiveData
    }
}