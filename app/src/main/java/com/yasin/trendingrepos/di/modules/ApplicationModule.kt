package com.yasin.trendingrepos.di.modules

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import com.yasin.trendingrepos.di.AppScope
import com.yasin.trendingrepos.di.ApplicationContext
import com.yasin.trendingrepos.network.GithubServices
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * Created by Yasin on 28/4/20.
 */
@Module(includes = [ContextModule::class])
class ApplicationModule {

    @Provides
    @AppScope
    fun provideExecutor(): Executor? {
        return Executors.newSingleThreadExecutor()
    }

    @Provides
    @AppScope
    fun familyBookServices(retrofit: Retrofit): GithubServices {
        return retrofit.create<GithubServices>(GithubServices::class.java)
    }

    @Provides
    @AppScope
    fun gson(): Gson {
        val gsonBuilder = GsonBuilder()
            .setDateFormat("yyyy-MM-dd")
        return gsonBuilder.create()
    }

    @Provides
    @AppScope
    fun picasso(@ApplicationContext context: Context): Picasso {
        return Picasso.Builder(context)
            .loggingEnabled(true)
            .downloader(OkHttp3Downloader(context))
            .build()
    }

}