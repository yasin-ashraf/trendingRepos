package com.yasin.trendingrepos.data.dataBase.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Yasin on 29/4/20.
 */
@Entity
data class OwnerDb(
    @PrimaryKey
    val id : Int,
    val login: String,
    val reposUrl: String,
    val type: String,
    val url: String,
    val avatarUrl: String,
    val htmlUrl: String,
    val organizationsUrl: String
)