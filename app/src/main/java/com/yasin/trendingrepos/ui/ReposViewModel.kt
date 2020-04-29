package com.yasin.trendingrepos.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.yasin.trendingrepos.data.dataBase.entity.OwnerDb
import com.yasin.trendingrepos.data.dataBase.entity.RepositoryDb
import com.yasin.trendingrepos.data.dataBase.entity.SearchResultDb
import com.yasin.trendingrepos.network.NetworkState
import com.yasin.trendingrepos.ui.home.HomeViewState
import com.yasin.trendingrepos.ui.uiDataModel.OwnerUi
import com.yasin.trendingrepos.ui.uiDataModel.RepositoryUi
import javax.inject.Inject

/**
 * Created by Yasin on 28/4/20.
 */
class ReposViewModel @Inject constructor(
    private val repository: ReposRepository
) : ViewModel() {

    private val searchQuery : MutableLiveData<String> = MutableLiveData()
    private val reposDbResult : LiveData<NetworkState<SearchResultDb>> =
        Transformations.switchMap(searchQuery) {
            repository.getReposForSearchQuery(it)
        }
    val reposUi : LiveData<HomeViewState> = Transformations.map(reposDbResult) {
        composeUiResult(it)
    }

    private fun composeUiResult(it: NetworkState<SearchResultDb>?): HomeViewState {
        return when(it) {
            is NetworkState.Loading -> HomeViewState.Loading
            is NetworkState.Success -> {
                val repositoriesList = mutableListOf<RepositoryUi>()
                it.data.repositories.forEach {
                    repositoriesList.add(it.convertToUi())
                }
                HomeViewState.Success(repositoriesList)
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