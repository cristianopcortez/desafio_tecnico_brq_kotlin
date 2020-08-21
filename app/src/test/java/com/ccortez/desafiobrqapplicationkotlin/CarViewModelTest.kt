package com.ccortez.desafiobrqapplicationkotlin

import android.app.Application
import android.content.Context
import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ccortez.desafiobrqapplicationkotlin.database.AppDatabase
import com.ccortez.desafiobrqapplicationkotlin.service.model.Car
import com.ccortez.desafiobrqapplicationkotlin.service.repository.CarRepository
import com.ccortez.desafiobrqapplicationkotlin.utils.TestCoroutineRule
import com.ccortez.desafiobrqapplicationkotlin.viewmodel.CarListViewModel
import com.ccortez.desafiobrqapplicationkotlin.viewmodel.CarViewModel
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.annotation.Config
//@Config(maxSdk = Build.VERSION_CODES.O_MR1)
@Config(maxSdk = Build.VERSION_CODES.P)
@RunWith(AndroidJUnit4::class)
class CarViewModelTest {

    @get:Rule
    val exceptionRule = ExpectedException.none()

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private lateinit var db: AppDatabase
    private lateinit var context: Context

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    }

    @After
    fun teardown() {
        db.close()
    }

    @Test
    fun test_getCarDetails() {

        val mutableCarList: MutableLiveData<List<Car>> = MutableLiveData()
        mutableCarList.value = arrayListOf(
            Car(1,"Corsa Hatch", "Chevrolet", "Carro", 1, 10000, ""),
            Car(2,"Corsa Super", "Chevrolet", "Carro", 1, 10000, ""),
            Car(3,"Corsa SW", "Chevrolet", "Carro", 1, 10000, "")
        )

        val mutableCar: MutableLiveData<Car> = MutableLiveData()
        mutableCar.value = Car(1,"Corsa Hatch", "Chevrolet", "Carro", 1, 10000, "")

        val carList: List<Car> = arrayListOf(
            Car(1,"Corsa Hatch", "Chevrolet", "Carro", 1, 10000, ""),
            Car(2,"Corsa Super", "Chevrolet", "Carro", 1, 10000, ""),
            Car(3,"Corsa SW", "Chevrolet", "Carro", 1, 10000, "")
        )

        val repository: CarRepository = mock()
//        var liveDataCar : LiveData<Car?> = carList.get(0) as LiveData<Car?>
        whenever(repository.getCarDetails(carList.get(0).id.toString()))
            .thenReturn(
                mutableCar
            )

        val application = Mockito.mock(Application::class.java)
        whenever(application.applicationContext)
            .thenReturn(
                context
            )

        val model = CarViewModel(repository, application)

        testCoroutineRule.runBlockingTest {
            model.setCarID(carList.get(0).id.toString())
            model.getCarDetails(repository)

            verify(repository)
                .getCarDetails(
                    model.carID.value
                )

        }

    }


}