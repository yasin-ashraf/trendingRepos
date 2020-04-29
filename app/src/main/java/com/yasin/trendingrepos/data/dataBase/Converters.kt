package com.yasin.trendingrepos.data.dataBase

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.yasin.trendingrepos.data.dataBase.entity.OwnerDb
import com.yasin.trendingrepos.data.dataBase.entity.RepositoryDb
import java.util.*

/**
 * Created by Yasin on 29/4/20.
 */
class Converters {

    @TypeConverter
    fun toDate(timestamp: Long?): Date? {
        return timestamp?.let { Date(it) }
    }

    @TypeConverter
    fun toTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun fromRepositoriesList(repositories: List<RepositoryDb?>?): String? {
        if (repositories == null) {
            return null
        }
        val gson = Gson()
        val type =
            object : TypeToken<List<RepositoryDb?>?>() {}.type
        return gson.toJson(repositories, type)
    }

    @TypeConverter
    fun toFirstCategoryList(repositories: String?): List<RepositoryDb?>? {
        if (repositories == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<RepositoryDb?>?>() {}.type
        return gson.fromJson<List<RepositoryDb?>>(
            repositories,
            type
        )
    }

    @TypeConverter
    fun fromOwner(ownerDb: OwnerDb?): String? {
        if (ownerDb == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<OwnerDb?>() {}.type
        return gson.toJson(ownerDb, type)
    }

    @TypeConverter
    fun toOwner(owner: String?): OwnerDb? {
        if (owner == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<OwnerDb?>() {}.type
        return gson.fromJson(owner, type)
    }
}