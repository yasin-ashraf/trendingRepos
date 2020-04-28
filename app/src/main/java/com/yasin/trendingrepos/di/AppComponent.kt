package com.yasin.trendingrepos.di

import com.yasin.trendingrepos.di.modules.ApplicationModule
import com.yasin.trendingrepos.di.modules.ContextModule
import com.yasin.trendingrepos.di.modules.NetworkModule
import com.yasin.trendingrepos.di.modules.RetrofitModule
import dagger.Component

/**
 * Created by Yasin on 28/4/20.
 */
@AppScope
@Component(
    modules = [ApplicationModule::class,ContextModule::class,NetworkModule::class,RetrofitModule::class]
)
interface AppComponent {

}