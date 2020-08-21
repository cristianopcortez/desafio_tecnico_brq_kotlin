package com.ccortez.desafiobrqapplicationkotlin.dao

import androidx.room.*
import com.ccortez.desafiobrqapplicationkotlin.service.model.Car

@Dao
interface CarDao {
    @Insert
    fun insert(car: Car?)

    @Update
    fun update(car: Car?)

    @Delete
    fun delete(car: Car?)

    @Query("DELETE FROM car")
    fun clear()

    @get:Query("SELECT * FROM car")
    val allCars: List<Car>

    @Query("SELECT * FROM car WHERE id = :id")
    fun getCarById(id: Long): Car?

    @get:Query("SELECT SUM(car.preco)FROM car")
    val sumFromShopCart: Int
}