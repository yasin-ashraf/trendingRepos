package com.yasin.trendingrepos.data.dataBase

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.yasin.trendingrepos.data.dataBase.dao.ReposDao
import com.yasin.trendingrepos.data.dataBase.dao.SearchResultsDao
import com.yasin.trendingrepos.data.dataBase.entity.RepositoryDb
import com.yasin.trendingrepos.data.dataBase.entity.SearchResultDb

/**
 * Created by Yasin on 28/4/20.
 */
@Database(entities = [SearchResultDb::class, RepositoryDb::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class ReposDatabase : RoomDatabase() {
    abstract fun searchResultsDao() : SearchResultsDao
    abstract fun reposDao(): ReposDao
}