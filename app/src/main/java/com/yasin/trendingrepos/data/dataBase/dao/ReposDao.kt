package com.yasin.trendingrepos.data.dataBase.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yasin.trendingrepos.data.dataBase.entity.RepositoryDb

/**
 * Created by Yasin on 29/4/20.
 */
@Dao
interface ReposDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveSearchResult(repo: RepositoryDb)

    @Query("SELECT * FROM RepositoryDb  WHERE id = :id")
    fun getRepoDetails(id : Int) : LiveData<RepositoryDb>
}