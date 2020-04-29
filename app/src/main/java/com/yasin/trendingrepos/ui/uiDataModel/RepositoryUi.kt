package com.yasin.trendingrepos.ui.uiDataModel

/**
 * Created by Yasin on 29/4/20.
 */
data class RepositoryUi(
    val id: Int = 0,
    val fullName: String = "",
    val pushedAt: String = "",
    val language: String = "",
    val name: String = "",
    val description: String = "",
    val updatedAt: String = "",
    val watchersCount: Int = 0,
    val watchers: Int = 0,

    val subscriptionUrl: String = "",

    val issueCommentUrl: String = "",
    val forks: Int = 0,

    val htmlUrl: String = "",
    val collaboratorsUrl: String = "",
    val cloneUrl: String = "",
    val private: Boolean = false,
    val contributorsUrl: String = "",
    val openIssuesCount: Int = 0,
    val createdAt: String = "",
    val gitUrl: String = "",
    val owner: OwnerUi? = null,
    val openIssues: Int = 0,
    val homepage: String = ""
)