package com.yasin.trendingrepos.di

import android.content.Context
import com.yasin.trendingrepos.di.modules.*
import com.yasin.trendingrepos.ui.contributor.ContributorScreen
import com.yasin.trendingrepos.ui.detail.DetailsScreen
import com.yasin.trendingrepos.ui.home.HomeScreen
import dagger.BindsInstance
import dagger.Component

/**
 * Created by Yasin on 28/4/20.
 */
@AppScope
@Component(
    modules = [ApplicationModule::class,ContextModule::class,NetworkModule::class,RetrofitModule::class,
    DatabaseModule::class]
)
interface AppComponent {

    fun injectHomeScreen(homeScreen: HomeScreen)
    fun injectDetailScreen(detailsScreen: DetailsScreen)
    fun injectContributorScreen(contributorScreen: ContributorScreen)

    @Component.Builder
    interface Builder {
        fun build() : AppComponent
        @BindsInstance fun context(@ApplicationContext context: Context) : Builder
    }

}