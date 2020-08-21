package com.ccortez.desafiobrqapplicationkotlin.di.module

import com.ccortez.desafiobrqapplicationkotlin.view.ui.CarFragment
import com.ccortez.desafiobrqapplicationkotlin.view.ui.CarListFragment
import com.ccortez.desafiobrqapplicationkotlin.view.ui.ShopCartListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeCarFragment(): CarFragment

    @ContributesAndroidInjector
    abstract fun contributeCarListFragment(): CarListFragment

    @ContributesAndroidInjector
    abstract fun contributeShopCartListFragment(): ShopCartListFragment
}