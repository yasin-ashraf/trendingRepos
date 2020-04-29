package com.yasin.trendingrepos.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.yasin.trendingrepos.data.dataBase.entity.RepositoryDb
import javax.inject.Inject

/**
 * Created by Yasin on 29/4/20.
 */
class DetailsViewModel @Inject constructor(
    private val detailsRepository: DetailsRepository
) : ViewModel() {

    private val id : MutableLiveData<Int> = MutableLiveData()
    val repoDetailsDb : LiveData<RepositoryDb> = Transformations.switchMap(id) {
        detailsRepository.getDbDetails(it)
    }

    fun setId(id : Int) {
        this.id.value = id
    }
}