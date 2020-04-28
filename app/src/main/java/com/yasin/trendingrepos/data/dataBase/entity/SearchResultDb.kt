package com.yasin.trendingrepos.data.dataBase.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Yasin on 28/4/20.
 */
@Entity
data class SearchResultDb(
    @PrimaryKey
    val id : String
)