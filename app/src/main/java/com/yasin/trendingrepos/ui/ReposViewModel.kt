package com.yasin.trendingrepos.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.yasin.trendingrepos.data.dataBase.entity.SearchResultDb
import com.yasin.trendingrepos.network.NetworkState
import javax.inject.Inject

/**
 * Created by Yasin on 28/4/20.
 */
class ReposViewModel @Inject constructor(
    private val repository: ReposRepository
) : ViewModel() {

    fun searchRepos(searchQuery : String) : LiveData<NetworkState<SearchResultDb>>{
        return repository.getReposForSearchQuery(searchQuery)
    }

}