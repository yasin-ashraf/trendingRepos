package com.yasin.trendingrepos.di.modules

import android.content.Context
import androidx.room.Room
import com.yasin.trendingrepos.data.dataBase.ReposDatabase
import com.yasin.trendingrepos.data.dataBase.dao.ReposSearchDao
import com.yasin.trendingrepos.di.AppScope
import com.yasin.trendingrepos.utils.DATABASE_NAME
import dagger.Module
import dagger.Provides

/**
 * Created by Yasin on 28/4/20.
 */
@Module(includes = [NetworkModule::class])
class DatabaseModule {

    @Provides
    @AppScope
    fun provideDatabase(context: Context) : ReposDatabase {
        return Room.databaseBuilder(context,ReposDatabase::class.java, DATABASE_NAME)
            .build()
    }

    @Provides
    @AppScope
    fun provideFormsDao(reposDatabase: ReposDatabase) : ReposSearchDao {
        return reposDatabase.reposSearchDao()
    }

}