package com.yasin.trendingrepos.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

/**
 * Created by Yasin on 28/4/20.
 */
class ReposViewModelFactory @Inject constructor(
    private val reposRepository: ReposRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ReposViewModel(
            reposRepository
        ) as T
    }
}