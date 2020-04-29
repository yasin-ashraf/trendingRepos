package com.yasin.trendingrepos.ui.home

import com.yasin.trendingrepos.ui.uiDataModel.RepositoryUi

/**
 * Created by Yasin on 29/4/20.
 */
sealed class HomeViewState {
    object Loading : HomeViewState()
    data class Success(val repos : List<RepositoryUi>) : HomeViewState()
    data class Error(val errorMsg : String) : HomeViewState()
}