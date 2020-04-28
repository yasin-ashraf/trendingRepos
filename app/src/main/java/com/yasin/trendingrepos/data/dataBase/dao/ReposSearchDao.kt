package com.yasin.trendingrepos.data.dataBase.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yasin.trendingrepos.data.dataBase.entity.SearchResultDb

/**
 * Created by Yasin on 28/4/20.
 */
@Dao
interface ReposSearchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveSearchResult(result: SearchResultDb)

    @Query("DELETE FROM SearchResultDb WHERE id = :id")
    fun deleteSearchResult(id : String)

    @Query("SELECT * FROM SearchResultDb")
    fun getSearchResult() : LiveData<SearchResultDb>

}