package com.yasin.trendingrepos.data.dataBase

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.yasin.trendingrepos.data.dataBase.dao.ReposSearchDao
import com.yasin.trendingrepos.data.dataBase.entity.SearchResultDb

/**
 * Created by Yasin on 28/4/20.
 */
@Database(entities = [SearchResultDb::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class ReposDatabase : RoomDatabase() {
    abstract fun reposSearchDao() : ReposSearchDao
}