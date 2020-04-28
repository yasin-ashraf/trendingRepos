package com.yasin.trendingrepos.di.modules

import android.content.Context
import com.yasin.trendingrepos.di.AppScope
import com.yasin.trendingrepos.di.ApplicationContext
import dagger.Binds
import dagger.Module

/**
 * Created by Yasin on 28/4/20.
 */
@Module
abstract class ContextModule {

    @AppScope
    @Binds
    abstract fun provideContext(@ApplicationContext context: Context): Context

}