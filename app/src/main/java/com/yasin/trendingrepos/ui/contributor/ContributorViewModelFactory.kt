package com.yasin.trendingrepos.ui.contributor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

/**
 * Created by Yasin on 29/4/20.
 */
class ContributorViewModelFactory @Inject constructor(
    private val contributorRepository: ContributorRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ContributorViewModel(
            contributorRepository
        ) as T
    }
}