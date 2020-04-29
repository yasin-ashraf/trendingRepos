package com.yasin.trendingrepos.data.dataBase.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.yasin.trendingrepos.data.dataBase.Converters
import java.util.*

/**
 * Created by Yasin on 28/4/20.
 */
@Entity
data class SearchResultDb(
    @PrimaryKey
    val id : String,
    @TypeConverters(Converters::class)
    val lastRefresh : Date,
    @TypeConverters(Converters::class)
    val repositories : List<RepositoryDb>?
)