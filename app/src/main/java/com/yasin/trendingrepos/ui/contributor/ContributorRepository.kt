package com.yasin.trendingrepos.ui.contributor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yasin.trendingrepos.data.dataBase.dao.ReposDao
import com.yasin.trendingrepos.data.models.Repository
import com.yasin.trendingrepos.network.GithubServices
import com.yasin.trendingrepos.network.NetworkState
import com.yasin.trendingrepos.utils.convertToDb
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executor
import javax.inject.Inject

/**
 * Created by Yasin on 29/4/20.
 */
class ContributorRepository @Inject constructor(
    private val githubServices: GithubServices,
    private val reposDao: ReposDao,
    private val executor: Executor
) {

    fun getContributorRepos(url: String): LiveData<NetworkState<List<Repository>>> {
        val contributors: MutableLiveData<NetworkState<List<Repository>>> = MutableLiveData()
        contributors.value = NetworkState.Loading
        githubServices.getContributorRepos(url).enqueue(object : Callback<List<Repository>> {
            override fun onResponse(
                call: Call<List<Repository>>,
                response: Response<List<Repository>>
            ) {
                if (response.isSuccessful) {
                    if (!response.body().isNullOrEmpty()) {
                        response.body()?.forEach {
                            executor.execute {
                                reposDao.saveSearchResult(it.convertToDb())
                            }
                        }
                    }
                    contributors.value = NetworkState.Success(response.body())
                } else {
                    contributors.value = NetworkState.Error(response.message())
                }
            }

            override fun onFailure(call: Call<List<Repository>>, t: Throwable) {
                contributors.value = NetworkState.Error(t.message ?: "Network Error!!")
            }
        })
        return contributors
    }
}