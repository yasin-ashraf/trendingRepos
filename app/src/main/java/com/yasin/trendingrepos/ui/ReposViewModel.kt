package com.yasin.trendingrepos.ui

import androidx.lifecycle.*
import com.yasin.trendingrepos.data.dataBase.entity.OwnerDb
import com.yasin.trendingrepos.data.dataBase.entity.RepositoryDb
import com.yasin.trendingrepos.data.dataBase.entity.SearchResultDb
import com.yasin.trendingrepos.network.NetworkState
import com.yasin.trendingrepos.ui.home.HomeViewState
import com.yasin.trendingrepos.ui.uiDataModel.OwnerUi
import com.yasin.trendingrepos.ui.uiDataModel.RepositoryUi
import java.util.*
import javax.inject.Inject

/**
 * Created by Yasin on 28/4/20.
 */
class ReposViewModel @Inject constructor(
    private val repository: ReposRepository
) : ViewModel() {

    private val forceRefresh : MutableLiveData<Boolean> = MutableLiveData()
    private val searchQuery : MutableLiveData<String> = MutableLiveData()
    private val refreshResult = MediatorLiveData<Pair<String, Boolean>>().apply {
        addSource(forceRefresh) {
            value = Pair(searchQuery.value ?: "",it)
        }
        addSource(searchQuery) {
            value = Pair(it ?: "",forceRefresh.value ?: false)
        }
    }
    private val reposDbResult : LiveData<NetworkState<SearchResultDb>> =
        Transformations.switchMap(refreshResult) {
            repository.getReposForSearchQuery(it.first.toLowerCase(Locale.getDefault()), it.second)
        }
    val reposUi : LiveData<HomeViewState> = Transformations.map(reposDbResult) {
        composeUiResult(it)
    }

    private fun composeUiResult(it: NetworkState<SearchResultDb>?): HomeViewState {
        return when(it) {
            is NetworkState.Loading -> HomeViewState.Loading
            is NetworkState.Success -> {
                val repositoriesList = mutableListOf<RepositoryUi>()
                it.data?.repositories?.forEach {
                    repositoriesList.add(it.convertToUi())
                }
                HomeViewState.Success(repositoriesList.sortedByDescending { it.watchersCount })
            }
            is NetworkState.Error -> {
                HomeViewState.Error(it.message)
            }
            else -> error("invalid network state $it")
        }
    }

    fun searchRepository(query: String) {
        this.searchQuery.value = query
    }

    fun forceRefresh(refresh : Boolean) {
        if(searchQuery.value.isNullOrBlank()) return
        this.forceRefresh.value = refresh
    }

    private fun RepositoryDb.convertToUi() : RepositoryUi {
        return RepositoryUi(
            id = id,
            fullName = fullName,
            pushedAt = pushedAt,
            language = language,
            name = name,
            description = description,
            owner = owner?.convertToUi(),
            watchersCount = watchersCount
        )
    }

    private fun OwnerDb.convertToUi() : OwnerUi {
        return OwnerUi(
            login,
            reposUrl,
            type,
            url,
            avatarUrl,
            htmlUrl,
            id,
            organizationsUrl
        )
    }

}