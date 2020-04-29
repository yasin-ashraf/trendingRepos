package com.yasin.trendingrepos.data.dataBase.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.yasin.trendingrepos.data.dataBase.Converters

/**
 * Created by Yasin on 29/4/20.
 */
@Entity
data class RepositoryDb(
    @PrimaryKey
    val id : Int,
    val pushedAt: String,
    val language: String,
    val fullName: String,
    val description: String,
    val watchersCount: Int,
    val url: String,
    val name: String,
    @TypeConverters(Converters::class)
    val owner: OwnerDb?,
    val contributorsUrl: String/*,

    val issueCommentUrl: String,
    val forks: Int,
    val htmlUrl: String,
    val collaboratorsUrl: String,
    val cloneUrl: String,
    val private: Boolean,
    val contributorsUrl: String,
    val openIssuesCount: Int,
    val createdAt: String,
    val watchers: Int,
    val updatedAt: String,
    val gitUrl: String,
    val openIssues: Int,
    val homepage: String*/
)