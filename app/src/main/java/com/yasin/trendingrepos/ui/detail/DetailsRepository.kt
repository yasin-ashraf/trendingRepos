package com.yasin.trendingrepos.ui.detail

import androidx.lifecycle.LiveData
import com.yasin.trendingrepos.data.dataBase.dao.ReposDao
import com.yasin.trendingrepos.data.dataBase.entity.RepositoryDb
import com.yasin.trendingrepos.network.GithubServices
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
}