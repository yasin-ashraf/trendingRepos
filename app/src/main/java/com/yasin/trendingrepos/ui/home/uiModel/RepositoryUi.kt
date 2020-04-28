package com.yasin.trendingrepos.ui.home.uiModel

/**
 * Created by Yasin on 29/4/20.
 */
data class RepositoryUi(
    val pushedAt: String = "",
    val subscriptionUrl: String = "",
    val language: String = "",
    val issueCommentUrl: String = "",
    val id: Int = 0,
    val forks: Int = 0,
    val fullName: String = "",
    val htmlUrl: String = "",
    val collaboratorsUrl: String = "",
    val cloneUrl: String = "",
    val name: String = "",
    val private: Boolean = false,
    val contributorsUrl: String = "",
    val openIssuesCount: Int = 0,
    val description: String = "",
    val createdAt: String = "",
    val watchers: Int = 0,
    val updatedAt: String = "",
    val gitUrl: String = "",
    val owner: OwnerUi? = null,
    val openIssues: Int = 0,
    val watchersCount: Int = 0,
    val homepage: String = ""
)