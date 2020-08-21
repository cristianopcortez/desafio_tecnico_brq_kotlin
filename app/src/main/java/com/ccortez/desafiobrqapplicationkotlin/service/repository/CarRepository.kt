package com.ccortez.desafiobrqapplicationkotlin.service.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ccortez.desafiobrqapplicationkotlin.dao.CarDao
import com.ccortez.desafiobrqapplicationkotlin.database.AppDatabase
import com.ccortez.desafiobrqapplicationkotlin.database.AppDatabase.Companion.getAppDatabase
import com.ccortez.desafiobrqapplicationkotlin.service.model.Car
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CarRepository {

    private var backEndService: BackEndService? = null
    var db: AppDatabase? = null
    var mContext: Context? = null

    @Inject
    constructor(backEndService: BackEndService?) {
        this.backEndService = backEndService
    }

    constructor() {}

    constructor(db: AppDatabase) {
        this.db = db
    }

    fun clearCars(carDao: CarDao) {
        carDao.clear()
    }

    fun removeCarFromCart(car: Car) {
        db!!.carDao()?.delete(car)
    }

    // TODO better error handling in part #2 ...
    fun carList(): LiveData<List<Car>> {
            val data =
                MutableLiveData<List<Car>>()
            backEndService?.carList()
                ?.enqueue(object : Callback<List<Car>> {
                    override fun onResponse(
                        call: Call<List<Car>>,
                        response: Response<List<Car>>
                    ) {
                        Log.d(TAG, "carList: " + response.body())
                        data.value = response.body()
                    }

                    override fun onFailure(
                        call: Call<List<Car>>,
                        t: Throwable
                    ) {
                        Log.e(TAG, "error carList: ", t)
                        // TODO better error handling in part #2 ...
                        data.value = null
                    }
                })
            return data
        }

    fun getCarDetails(carID: String?): LiveData<Car?> {
        val data = MutableLiveData<Car?>()
        backEndService!!.getCarDetails(carID)!!.enqueue(object : Callback<Car?> {
            override fun onResponse(
                call: Call<Car?>,
                response: Response<Car?>
            ) {
                simulateDelay()
                data.value = response.body()
            }

            override fun onFailure(
                call: Call<Car?>,
                t: Throwable
            ) { // TODO better error handling in part #2 ...
                data.value = null
            }
        })
        return data
    }

    private fun simulateDelay() {
        try {
            Thread.sleep(10)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    fun getCarDbList(mContext: Context?): LiveData<List<Car>> {
        val data =
            MutableLiveData<List<Car>>()
        if (mContext != null) {
            if (db == null)
                db = getAppDatabase(mContext.applicationContext)
            data.postValue(db!!.carDao()!!.allCars)
        }
        return data
    }

    fun getAllCars(mContext: Context?): List<Car>? {
        if (mContext != null) {
            if (db == null)
                db = getAppDatabase(mContext.applicationContext)
            return db!!.carDao()!!.allCars
        }
        return null
    }

    companion object {
        private val TAG = CarRepository::class.java.simpleName
    }
}