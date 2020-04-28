package com.yasin.trendingrepos.models

import com.google.gson.annotations.SerializedName

data class RepoSearchResult(
    @SerializedName("total_count")
    val totalCount: Int = 0,
    @SerializedName("incomplete_results")
    val incompleteResults: Boolean = false,
    @SerializedName("items")
    val items: List<Repository>?
)