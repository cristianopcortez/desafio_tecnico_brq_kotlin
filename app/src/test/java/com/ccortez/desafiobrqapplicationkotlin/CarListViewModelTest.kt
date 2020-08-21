package com.ccortez.desafiobrqapplicationkotlin

import android.app.Application
import android.content.Context
import android.os.Build
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ccortez.desafiobrqapplicationkotlin.database.AppDatabase
import com.ccortez.desafiobrqapplicationkotlin.service.model.Car
import com.ccortez.desafiobrqapplicationkotlin.service.repository.CarRepository
import com.ccortez.desafiobrqapplicationkotlin.viewmodel.CarListViewModel
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.annotation.Config

//@Config(maxSdk = Build.VERSION_CODES.O_MR1)
@Config(maxSdk = Build.VERSION_CODES.P)
@RunWith(AndroidJUnit4::class)
class CarListViewModelTest {

    @get:Rule
    val exceptionRule = ExpectedException.none()

    private lateinit var db: AppDatabase
    private lateinit var context: Context
    private lateinit var application: Application

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        application = Mockito.mock(TestMVVMApplication::class.java)

        whenever(application.applicationContext)
            .thenReturn(application)
    }

    @After
    fun teardown() {
        db.close()
    }

    @Test
    fun test_carShopCartListObservableEmpty() {
//        val application = Mockito.mock(MVVMApplication::class.java)
//        val application = Mockito.mock(Application::class.java)
//        val context = ApplicationProvider.getApplicationContext<Context>()
//        val context = Mockito.mock(Context::class.java)

//        val repository: CarRepository = mock()
        val repository = CarRepository(db)

//        whenever(repository.getCarDbList(context))
//            .thenReturn(MutableLiveData<List<Car>>())

//        whenever(application.applicationContext)
//            .thenReturn(application)

        val expected = 0
        val model = CarListViewModel(repository, application)

        println("context: "+context)
        println("model: "+model)
        println("model.getCarDbListObservable(context): "+model.getCarDbListObservable(context))

        val mutableCarList: MutableLiveData<List<Car>> = MutableLiveData()
        val carListEmpty: List<Car>? = arrayListOf()

//        whenever(model.getCarDbListObservable(context))
//            .thenReturn(mutableCarList)
//        Mockito.`when`(model.getCarDbListObservable(context))
//            .thenReturn(mutableCarList)
//        whenever(model.getCarDbListObservable(context).value)
//            .thenReturn(carListEmpty)

        val carList = model.getCarDbListObservable(context).value

        println("carList: "+carList)
        println("carList size: "+carList!!.size)

        assertNotNull(carList)
        assertEquals(expected, carList!!.size)
    }

    @Test
    fun test_carShopCartListObservableMultiple() {
//        val application = Mockito.mock(MVVMApplication::class.java)
//        val application = Mockito.mock(Application::class.java)
//        val context = ApplicationProvider.getApplicationContext<Context>()
//        val context = Mockito.mock(Context::class.java)

        println("context: "+context)

//        val repository: CarRepository = mock(verboseLogging = true)
        val repository = CarRepository(db)

        println("repository: "+repository)

//        whenever(repository.getCarDbList(context))
//            .thenReturn(MutableLiveData<List<Car>>())

//        whenever(application.applicationContext)
//            .thenReturn(application)
//
//        val mutableCarList: MutableLiveData<List<Car>> = MutableLiveData()
//        mutableCarList.value = arrayListOf(
//            Car(1,"Corsa Hatch", "Chevrolet", "Carro", 1, 10000, ""),
//            Car(2,"Corsa Super", "Chevrolet", "Carro", 1, 10000, ""),
//            Car(3,"Corsa SW", "Chevrolet", "Carro", 1, 10000, "")
//        )

        db.carDao()?.insert(Car(1,"Corsa Hatch", "Chevrolet", "Carro", 1, 10000, ""))
        db.carDao()?.insert(Car(2,"Corsa Super", "Chevrolet", "Carro", 1, 10000, ""))
        db.carDao()?.insert(Car(3,"Corsa SW", "Chevrolet", "Carro", 1, 10000, ""))

//        println("mutableCarList: "+mutableCarList)
//        println("mutableCarList.value: "+mutableCarList.value)

        whenever(application.applicationContext)
            .thenReturn(
                context
            )

        val expected = 3
        val model = CarListViewModel(repository, application)

        println("model: "+model)
        println("model.getCarDbListObservable(context): "+model.getCarDbListObservable(context))

        val carList = model.getCarDbListObservable(context).value

        println("carList: "+carList)
        println("carList size: "+carList!!.size)

        assertNotNull(carList)
        assertEquals(expected, carList!!.size)

    }

    @Test
    fun test_carDetailsToggleRemoveFromCart() {
//        val application = Mockito.mock(MVVMApplication::class.java)
//        val application = Mockito.mock(Application::class.java)
//        val context = ApplicationProvider.getApplicationContext<Context>()
//        val context = Mockito.mock(Context::class.java)

        println("context: "+context)

//        val repository: CarRepository = mock(verboseLogging = true)
        val repository = CarRepository(db)

        println("repository: "+repository)

//        whenever(repository.getCarDbList(context))
//            .thenReturn(MutableLiveData<List<Car>>())

//        whenever(application.applicationContext)
//            .thenReturn(application)
//
//        val mutableCarList: MutableLiveData<List<Car>> = MutableLiveData()
//        mutableCarList.value = arrayListOf(
//            Car(1,"Corsa Hatch", "Chevrolet", "Carro", 1, 10000, ""),
//            Car(2,"Corsa Super", "Chevrolet", "Carro", 1, 10000, ""),
//            Car(3,"Corsa SW", "Chevrolet", "Carro", 1, 10000, "")
//        )

        db.carDao()?.insert(Car(1,"Corsa Hatch", "Chevrolet", "Carro", 1, 10000, ""))
        db.carDao()?.insert(Car(2,"Corsa Super", "Chevrolet", "Carro", 1, 10000, ""))
        db.carDao()?.insert(Car(3,"Corsa SW", "Chevrolet", "Carro", 1, 10000, ""))

//        println("mutableCarList: "+mutableCarList)
//        println("mutableCarList.value: "+mutableCarList.value)

        whenever(application.applicationContext)
            .thenReturn(
                context
            )

        var expected = 3
        val model = CarListViewModel(repository, application)

        println("model: "+model)
        println("model.getCarDbListObservable(context): "+model.getCarDbListObservable(context))

        val carList = model.getCarDbListObservable(context).value

        println("carList: "+carList)
        println("carList size: "+carList!!.size)

        assertNotNull(carList)
        assertEquals(expected, carList!!.size)

        model.removeFromCart(db.carDao()!!.getCarById(
            carList.get(0).id
        ))

//        verify(repository)
//            .removeCarFromCart(Car(1,"Corsa Hatch", "Chevrolet", "Carro", 1, 10000, ""))

        expected = 2

        val carListAfterRemoval = repository.getCarDbList(context)

        println("carListAfterRemoval: "+carListAfterRemoval)
        println("carListAfterRemoval size: "+carListAfterRemoval!!.value?.size)

        assertNotNull(carListAfterRemoval)
        assertEquals(expected, carListAfterRemoval!!.value?.size)


    }

    @Test
    fun test_ToggleRemoveFromCart() {

        val mutableCarList: MutableLiveData<List<Car>> = MutableLiveData()
        mutableCarList.value = arrayListOf(
            Car(1,"Corsa Hatch", "Chevrolet", "Carro", 1, 10000, ""),
            Car(2,"Corsa Super", "Chevrolet", "Carro", 1, 10000, ""),
            Car(3,"Corsa SW", "Chevrolet", "Carro", 1, 10000, "")
        )

        val repository: CarRepository = mock()
        whenever(repository.getCarDbList(context))
            .thenReturn(
                mutableCarList
            )
        whenever(repository.carList())
            .thenReturn(
                mutableCarList
            )

//        val application = Mockito.mock(Application::class.java)
        whenever(application.applicationContext)
            .thenReturn(
                context
            )

        val model = CarListViewModel(repository, application)

        model.removeFromCart(
            Car(1,"Corsa Hatch", "Chevrolet", "Carro", 1, 10000, "")
        )

        verify(repository)
            .removeCarFromCart(
                Car(1,"Corsa Hatch", "Chevrolet", "Carro", 1, 10000, "")
            )
    }

    @Test
    fun test_carCarListObservableMultiple() {
//        val application = Mockito.mock(MVVMApplication::class.java)
//        val application = Mockito.mock(Application::class.java)
//        val context = ApplicationProvider.getApplicationContext<Context>()
//        val context = Mockito.mock(Context::class.java)

        println("context: "+context)

        val repository: CarRepository = mock(verboseLogging = true)
//        val repository = CarRepository(db)

        println("repository: "+repository)

        val mutableCarList: MutableLiveData<List<Car>> = MutableLiveData()
        mutableCarList.value = arrayListOf(
            Car(1,"Corsa Hatch", "Chevrolet", "Carro", 1, 10000, ""),
            Car(2,"Corsa Super", "Chevrolet", "Carro", 1, 10000, ""),
            Car(3,"Corsa SW", "Chevrolet", "Carro", 1, 10000, "")
        )

        val arrCarList = arrayListOf(
            Car(1,"Corsa Hatch", "Chevrolet", "Carro", 1, 10000, ""),
            Car(2,"Corsa Super", "Chevrolet", "Carro", 1, 10000, ""),
            Car(3,"Corsa SW", "Chevrolet", "Carro", 1, 10000, "")
        )

        whenever(repository.carList())
            .thenReturn(mutableCarList)

        println("mutableCarList: "+mutableCarList)
        println("mutableCarList.value: "+mutableCarList.value)

        whenever(application.applicationContext)
            .thenReturn(
                context
            )

        val expected = 3
        val model: CarListViewModel = mock(verboseLogging = true)

        whenever(model.carListObservable)
        .thenReturn(mutableCarList)

        println("model: "+model)
        println("model.carListObservable: "+model.carListObservable)

        val carList = model.carListObservable.value

        println("carList: "+carList)
        println("carList size: "+carList!!.size)

        assertNotNull(carList)
        assertEquals(expected, carList!!.size)

    }

}