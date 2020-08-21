package com.ccortez.desafiobrqapplicationkotlin.di.module

import android.content.Context
import com.ccortez.desafiobrqapplicationkotlin.MVVMApplication
import com.ccortez.desafiobrqapplicationkotlin.service.repository.BackEndService
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
internal class AppModule {

    @Singleton
    @Provides
    fun provideBackEndService(): BackEndService {
        val client = OkHttpClient.Builder().connectTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS).build()
        return Retrofit.Builder()
            .baseUrl(BackEndService.HTTP_API_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BackEndService::class.java)
    }

    @Provides
    @Singleton
    fun provideGson() = GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()!!

    @Provides
    @Singleton
    fun provideContext(mvvmApp: MVVMApplication): Context = mvvmApp


//    @Singleton
//    @Provides
//    fun provideViewModelFactory(
//        viewModelSubComponent: ViewModelSubComponent.Builder
//    ): ViewModelProvider.Factory {
//        return CarViewModelFactory(viewModelSubComponent.build())
//    }
}