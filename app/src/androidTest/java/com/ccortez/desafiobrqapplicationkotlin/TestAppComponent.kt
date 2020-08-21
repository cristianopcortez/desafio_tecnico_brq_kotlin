package com.ccortez.desafiobrqapplicationkotlin

import android.app.Application
import com.ccortez.desafiobrqapplicationkotlin.MVVMApplication
import com.ccortez.desafiobrqapplicationkotlin.di.module.AppModule
import com.ccortez.desafiobrqapplicationkotlin.di.module.MainActivityModule
import com.ccortez.desafiohurbapplicationkotlin.di.module.ViewModelFactoryModule
import com.ccortez.desafiohurbapplicationkotlin.di.module.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import javax.inject.Singleton

@Singleton
@Component(modules = [(AndroidInjectionModule::class), (AppModule::class),
    (ViewModelModule::class), (MainActivityModule::class),
    (ViewModelFactoryModule::class)])
interface TestAppComponent : AndroidInjector<DaggerApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: MVVMApplication): Builder

        fun build(): TestAppComponent
    }

    fun inject(mvvmApplication: MVVMApplication)

    override fun inject(test: DaggerApplication?)
}