package com.yasin.trendingrepos.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yasin.trendingrepos.data.dataBase.dao.ReposDao
import com.yasin.trendingrepos.data.dataBase.entity.RepositoryDb
import com.yasin.trendingrepos.data.models.Owner
import com.yasin.trendingrepos.network.GithubServices
import com.yasin.trendingrepos.network.NetworkState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

/**
 * Created by Yasin on 29/4/20.
 */
class DetailsRepository @Inject constructor(
    private val githubServices: GithubServices,
    private val reposDao: ReposDao
) {

    fun getDbDetails(id : Int) : LiveData<RepositoryDb> {
        return reposDao.getRepoDetails(id)
    }

    fun getContributors(url : String) : LiveData<NetworkState<List<Owner>>> {
        val contributors : MutableLiveData<NetworkState<List<Owner>>> = MutableLiveData()
        contributors.value = NetworkState.Loading
        githubServices.getContributors(url).enqueue(object : Callback<List<Owner>>{
            override fun onResponse(call: Call<List<Owner>>, response: Response<List<Owner>>) {
                if(response.isSuccessful) {
                    contributors.value = NetworkState.Success(response.body())
                }else {
                    contributors.value = NetworkState.Error(response.message())
                }
            }

            override fun onFailure(call: Call<List<Owner>>, t: Throwable) {
                contributors.value = NetworkState.Error(t.message ?: "Network Error!!")
            }
        })
        return contributors
    }
}