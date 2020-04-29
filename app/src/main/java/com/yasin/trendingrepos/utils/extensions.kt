package com.yasin.trendingrepos.utils

import com.yasin.trendingrepos.data.dataBase.entity.OwnerDb
import com.yasin.trendingrepos.data.dataBase.entity.RepositoryDb
import com.yasin.trendingrepos.data.models.Owner
import com.yasin.trendingrepos.data.models.Repository
import com.yasin.trendingrepos.ui.uiDataModel.OwnerUi
import com.yasin.trendingrepos.ui.uiDataModel.RepositoryUi
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Yasin on 29/4/20.
 */

fun String.dateToFormat(): String {
    var formattedDate = ""
    if (this.isNotEmpty() && this != "null") {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val outputFormat = SimpleDateFormat("EEE dd MMM yyyy", Locale.getDefault())
        val date = inputFormat.parse(this)
        formattedDate = outputFormat.format(date)
    }
    return formattedDate
}

fun Repository.convertToDb(): RepositoryDb {
    return RepositoryDb(
        id = id ?: 0,
        pushedAt = pushedAt ?: "",
        language = language ?: "",
        fullName = fullName ?: "",
        description = description ?: "",
        watchersCount = watchersCount ?: 0,
        contributorsUrl = contributorsUrl ?: "",
        name = name ?: "",
        owner = owner.convertToDb(),
        url = url ?: ""
    )
}

fun Owner.convertToDb(): OwnerDb {
    return OwnerDb(
        id = id ?: 0,
        login = login ?: "",
        reposUrl = reposUrl ?: "",
        type = type ?: "",
        url = url ?: "",
        avatarUrl = avatarUrl ?: "",
        htmlUrl = htmlUrl ?: "",
        organizationsUrl = organizationsUrl ?: ""
    )
}

fun RepositoryDb.convertToUi() : RepositoryUi {
    return RepositoryUi(
        id = id,
        fullName = fullName,
        pushedAt = pushedAt,
        language = language,
        name = name,
        description = description,
        owner = owner?.convertToUi(),
        watchersCount = watchersCount
    )
}

fun Repository.convertToUi() : RepositoryUi {
    return RepositoryUi(
        id = id ?: 0,
        fullName = fullName ?: "",
        pushedAt = pushedAt ?: "",
        language = language ?: "",
        name = name ?: "",
        description = description ?: "",
        owner = owner.convertToUi(),
        watchersCount = watchersCount ?: 0
    )
}

fun Owner.convertToUi() : OwnerUi {
    return OwnerUi(
        loginName = login ?: "",
        reposUrl = reposUrl ?: "",
        type = type ?: "",
        url = url ?: "",
        avatarUrl = avatarUrl ?: "",
        htmlUrl = htmlUrl ?: "",
        id = id ?: 0,
        organizationsUrl = organizationsUrl ?: ""
    )
}

fun OwnerDb.convertToUi() : OwnerUi {
    return OwnerUi(
        login,
        reposUrl,
        type,
        url,
        avatarUrl,
        htmlUrl,
        id,
        organizationsUrl
    )
}