package com.yasin.trendingrepos

import android.app.Application
import android.content.Context
import androidx.fragment.app.Fragment
import com.yasin.trendingrepos.di.AppComponent
import com.yasin.trendingrepos.di.DaggerAppComponent

/**
 * Created by Yasin on 28/4/20.
 */
class TrendingRepos : Application() {

    companion object {
        fun appComponent(context: Context) =
            (context.applicationContext as TrendingRepos).appComponent
    }

    private val appComponent : AppComponent by lazy {
        DaggerAppComponent.builder()
            .context(this.applicationContext)
            .build()
    }
}

fun Fragment.getAppComponent(context: Context) = TrendingRepos.appComponent(context)