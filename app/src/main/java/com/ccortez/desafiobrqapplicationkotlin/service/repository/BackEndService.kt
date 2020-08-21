package com.ccortez.desafiobrqapplicationkotlin.service.repository

import com.ccortez.desafiobrqapplicationkotlin.BuildConfig
import com.ccortez.desafiobrqapplicationkotlin.service.model.Car
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface BackEndService {

    //    @GET("carros.json")
    @GET(".")
    fun carList(): Call<List<Car>>

    //@GET("descricao_carro.json")
    @GET("{id}")
    fun getCarDetails(@Path("id") id: String?): Call<Car?>?

    companion object {
        const val HTTP_API_URL =
            BuildConfig.API_BASE_URL
    }
}