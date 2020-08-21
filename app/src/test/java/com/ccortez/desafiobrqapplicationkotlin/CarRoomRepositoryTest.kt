@file:Suppress("IncorrectScope")

package com.ccortez.desafiobrqapplicationkotlin

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ccortez.desafiobrqapplicationkotlin.database.AppDatabase
import com.ccortez.desafiobrqapplicationkotlin.service.CarService
import com.ccortez.desafiobrqapplicationkotlin.service.model.Car
import com.ccortez.desafiobrqapplicationkotlin.service.repository.CarRepository
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.verify
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

//@Config(maxSdk = Build.VERSION_CODES.O_MR1)
@Config(maxSdk = Build.VERSION_CODES.P)
@RunWith(AndroidJUnit4::class)
class CarRoomRepositoryTest {
    @get:Rule
    val testRule = InstantTaskExecutorRule()

    @get:Rule
    val exceptionRule = ExpectedException.none()

    val now = System.currentTimeMillis()
    val day = 1000 * 60 * 60 * 24

    private lateinit var db: AppDatabase

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    }

    @After
    fun teardown() {
        db.close()
    }

    @Test
    fun test_getCarCountEmpty() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        val dao = spy(db.carDao())
        val repo = CarRepository()
        val expected = 0

//        repo.clearCars(dao)
        db.carDao()?.clear()

//        println( "repo.getAllCars(context).size: "+repo.getAllCars(context).size)
        println( "db.carDao()?.allCars?.size: "+ (db.carDao()?.allCars?.size ?: 0))

//        val actual = repo.getUpcomingTodosCount().test().value()
//        val actual = repo.getAllCars(context).size
        val actual = (db.carDao()?.allCars?.size ?: 0)

        assertEquals(expected, actual)
//        verify(dao).getDateCount(any())
    }

    @Test
    fun test_getInsertOneCarOnCart() {

        val context = ApplicationProvider.getApplicationContext<Context>()

        db.carDao()?.insert(Car(1,"Corsa", "Chevrolet", "Carro", 1, 10000, ""))

        val dao = spy(db.carDao())
        val repo = CarRepository()
        val expected = 1

//        val actual = repo.getAllCars(context).size
        val actual = db.carDao()?.allCars?.size

        assertEquals(expected, actual)

    }

    @Test
    fun test_getVerifySumFromCart() {

        val context = ApplicationProvider.getApplicationContext<Context>()
        val dao = spy(db.carDao())
        val service = CarService(context, db)

        db.carDao()?.insert(Car(1, "Corsa", "Chevrolet", "Carro", 1, 10000, ""))
        db.carDao()?.insert(Car(2, "Celta", "Chevrolet", "Carro", 4, 20000, ""))
        db.carDao()?.insert(Car(3, "GT-R", "Nissan", "Carro", 4, 20000, ""))

        println("sumFromShopCart: "+service.sumFromShopCart)

        assertTrue(service.sumFromShopCart > 100000)

    }

    companion object {
        const val TAG = "CarRoomRepositoryTest"
    }

}