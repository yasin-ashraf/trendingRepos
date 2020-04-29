package com.yasin.trendingrepos.ui.contributor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.yasin.trendingrepos.data.dataBase.entity.RepositoryDb
import com.yasin.trendingrepos.data.models.Owner
import com.yasin.trendingrepos.data.models.Repository
import com.yasin.trendingrepos.network.NetworkState
import com.yasin.trendingrepos.ui.uiDataModel.RepositoryUi
import javax.inject.Inject

/**
 * Created by Yasin on 29/4/20.
 */
class ContributorViewModel @Inject constructor(
    private val contributorRepository: ContributorRepository
) : ViewModel(){

    private val url: MutableLiveData<String> = MutableLiveData()

    val repos: LiveData<NetworkState<List<Repository>>> =
        Transformations.switchMap(url) {
            contributorRepository.getContributorRepos(it)
        }

    fun setUrl(url: String) {
        this.url.value = url
    }

    fun refresh() {
        this.url.value = url.value
    }
}