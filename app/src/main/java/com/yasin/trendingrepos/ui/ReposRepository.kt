package com.yasin.trendingrepos.ui

import androidx.lifecycle.LiveData
import com.yasin.trendingrepos.data.dataBase.dao.ReposSearchDao
import com.yasin.trendingrepos.data.dataBase.entity.OwnerDb
import com.yasin.trendingrepos.data.dataBase.entity.RepositoryDb
import com.yasin.trendingrepos.data.dataBase.entity.SearchResultDb
import com.yasin.trendingrepos.data.models.Owner
import com.yasin.trendingrepos.data.models.RepoSearchResult
import com.yasin.trendingrepos.data.models.Repository
import com.yasin.trendingrepos.network.GithubServices
import com.yasin.trendingrepos.network.NetworkBoundResource
import com.yasin.trendingrepos.network.NetworkState
import com.yasin.trendingrepos.utils.FRESH_TIMEOUT_IN_MINUTES
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
    private val reposSearchDao: ReposSearchDao
) {

    fun getReposForSearchQuery(searchQuery : String) : LiveData<NetworkState<SearchResultDb>> {

        return object : NetworkBoundResource<SearchResultDb, RepoSearchResult>() {

            override fun saveCallResult(item: RepoSearchResult?) {
                reposSearchDao.saveSearchResult(item?.convertToDb() ?: SearchResultDb(1,Date(), mutableListOf()))
            }

            override fun shouldFetch(data: SearchResultDb?): Boolean {
                return (data?.lastRefresh ?: Date()).time - Date().time > FRESH_TIMEOUT_IN_MINUTES.toLong()
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

    private fun getMaxRefreshTime(currentDate: Date): Date? {
        val cal = Calendar.getInstance()
        cal.time = currentDate
        cal.add(Calendar.MINUTE, -FRESH_TIMEOUT_IN_MINUTES)
        return cal.time
    }

    private fun getRepositoriesDb(items: List<Repository>?): List<RepositoryDb> {
        val reposDbList = mutableListOf<RepositoryDb>()
        items?.take(10)?.forEach {
            reposDbList.add(it.convertToDb())
        }
        return reposDbList
    }

    /** Converters */

    private fun RepoSearchResult.convertToDb() : SearchResultDb {
        return SearchResultDb(
            id = 1,
            lastRefresh = Date(),
            repositories = getRepositoriesDb(items)
        )
    }

    private fun Repository.convertToDb() : RepositoryDb {
        return RepositoryDb(
            id = id ?: 1,
            pushedAt = pushedAt ?: "",
            language = language ?: "",
            fullName = fullName ?: "",
            description = description ?: "",
            watchersCount = watchersCount ?: 0,
            subscriptionUrl = subscriptionUrl ?: "",
            name = name ?: "",
            owner = owner.convertToDb()
        )
    }

    private fun Owner.convertToDb() : OwnerDb {
        return OwnerDb(
            id = id ?: 0,
            login = login ?: "",
            reposUrl = reposUrl ?: "",
            type = type ?: "",
            url = url ?: "",
            avatarUrl = avatarUrl ?: "",
            htmlUrl = htmlUrl ?: "",
            organizationsUrl = organizationsUrl ?: ""
        )
    }

}