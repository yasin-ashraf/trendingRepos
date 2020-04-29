package com.yasin.trendingrepos.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

/**
 * Created by Yasin on 29/4/20.
 */
class DetailViewModelFactory @Inject constructor(
    private val detailsRepository: DetailsRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailsViewModel(
            detailsRepository
        ) as T
    }
}