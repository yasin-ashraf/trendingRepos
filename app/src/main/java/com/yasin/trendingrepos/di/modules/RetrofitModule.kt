package com.yasin.trendingrepos.di.modules

import com.google.gson.Gson
import com.yasin.trendingrepos.di.AppScope
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Yasin on 28/4/20.
 */
@Module
class RetrofitModule {

    @Provides
    @AppScope
    fun retrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit {
        /* INFO: BASE URL FOR APP*/
        val baseUrl = ""

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .baseUrl(baseUrl)
            .build()
    }

}