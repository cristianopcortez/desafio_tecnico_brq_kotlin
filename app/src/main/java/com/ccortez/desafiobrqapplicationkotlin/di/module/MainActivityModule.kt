package com.ccortez.desafiobrqapplicationkotlin.di.module

import com.ccortez.desafiobrqapplicationkotlin.view.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityModule {
    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun contributeMainActivity(): MainActivity
}