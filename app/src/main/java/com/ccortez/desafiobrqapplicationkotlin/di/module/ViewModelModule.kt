package com.ccortez.desafiohurbapplicationkotlin.di.module

import android.app.Application
import androidx.lifecycle.ViewModel
import com.ccortez.desafiobrqapplicationkotlin.MVVMApplication
import com.ccortez.desafiobrqapplicationkotlin.di.ViewModelKey
import com.ccortez.desafiobrqapplicationkotlin.viewmodel.CarListViewModel
import com.ccortez.desafiobrqapplicationkotlin.viewmodel.CarViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindApplication(app: MVVMApplication?): Application?

    @Binds
    @IntoMap
    @ViewModelKey(CarListViewModel::class)
    abstract fun bindCarListViewModel(carListViewModel: CarListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CarViewModel::class)
    abstract fun bindCarViewModel(carViewModel: CarViewModel): ViewModel


}
