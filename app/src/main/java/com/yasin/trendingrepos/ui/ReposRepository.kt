package com.yasin.trendingrepos.ui

import androidx.lifecycle.LiveData
import com.yasin.trendingrepos.data.dataBase.dao.ReposDao
import com.yasin.trendingrepos.data.dataBase.dao.SearchResultsDao
import com.yasin.trendingrepos.data.dataBase.entity.RepositoryDb
import com.yasin.trendingrepos.data.dataBase.entity.SearchResultDb
import com.yasin.trendingrepos.data.models.RepoSearchResult
import com.yasin.trendingrepos.data.models.Repository
import com.yasin.trendingrepos.network.GithubServices
import com.yasin.trendingrepos.network.NetworkBoundResource
import com.yasin.trendingrepos.network.NetworkState
import com.yasin.trendingrepos.utils.FRESH_TIMEOUT_IN_MINUTES
import com.yasin.trendingrepos.utils.convertToDb
import retrofit2.Call
import java.util.*
import java.util.concurrent.Executor
import javax.inject.Inject

/**
 * Created by Yasin on 28/4/20.
 */
class ReposRepository @Inject constructor(
    private val githubServices: GithubServices,
    private val executor: Executor,
    private val searchResultsDao: SearchResultsDao,
    private val reposDao: ReposDao
) {

    fun getReposForSearchQuery(
        searchQuery: String,
        forceRefresh: Boolean
    ): LiveData<NetworkState<SearchResultDb>> {

        return object : NetworkBoundResource<SearchResultDb, RepoSearchResult>() {

            override fun saveCallResult(item: RepoSearchResult?) {
                //store search result
                searchResultsDao.saveSearchResult(
                    item?.convertToDb(searchQuery) ?: SearchResultDb(
                        searchQuery,
                        Date(),
                        mutableListOf()
                    )
                )
            }

            override fun shouldFetch(data: SearchResultDb?): Boolean {
                return forceRefresh ||
                        data == null || //no data found in db for keyword
                        data.lastRefresh.time - Date().time > FRESH_TIMEOUT_IN_MINUTES.toLong()
            }

            override fun loadFromDb(): LiveData<SearchResultDb> {
                return searchResultsDao.getSearchResult(searchQuery)
            }

            override fun createCall(): Call<RepoSearchResult> {
                return githubServices.getRepos(searchQuery, "stars", "desc", 1)
            }

            override fun onFetchFailed(code: Int, body: RepoSearchResult?) {
                if(code == 207) {

                }
            }

            override fun onNetworkError(t: Throwable) {

            }

        }.asLiveData
    }

    private fun getRepositoriesDb(items: List<Repository>?): List<RepositoryDb> {
        val reposDbList = mutableListOf<RepositoryDb>()
        items?.take(10)?.forEach {
            reposDbList.add(it.convertToDb().apply {
                executor.execute {
                    reposDao.saveSearchResult(this)
                }
            })
        }
        return reposDbList
    }

    /** Converters */

    private fun RepoSearchResult.convertToDb(searchQuery: String): SearchResultDb {
        return SearchResultDb(
            id = searchQuery,
            lastRefresh = Date(),
            repositories = getRepositoriesDb(items)
        )
    }

}