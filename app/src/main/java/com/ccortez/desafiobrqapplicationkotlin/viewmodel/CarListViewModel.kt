package com.ccortez.desafiobrqapplicationkotlin.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.ccortez.desafiobrqapplicationkotlin.service.model.Car
import com.ccortez.desafiobrqapplicationkotlin.service.repository.CarRepository
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class CarListViewModel @Inject constructor(
    carRepository: CarRepository,
    application: Application
) : AndroidViewModel(application) {
    /**
     * Expose the LiveData Cars query so the UI can observe it.
     */
    lateinit var carListObservable: LiveData<List<Car>>
    lateinit var carDbListObservable: LiveData<List<Car>>
    var carRepository = CarRepository()

    fun getCarDbListObservable(mContext: Context?): LiveData<List<Car>> {
        println("getCarDbListObservable: "+ carDbListObservable)
        return carDbListObservable
    }

    fun removeFromCart(car: Car?) {
        if (car != null) {
            carRepository.removeCarFromCart(car)
        }
//        db!!.carDao()?.delete(car)
    }

    init {
        this.carRepository = carRepository

        println("application: "+application)
        println("application.applicationContext: "+application.applicationContext)
        println("carRepository: "+carRepository)
        println("carRepository.getCarDbList(application.applicationContext): "+
                carRepository.getCarDbList(application.applicationContext))
        println("carRepository: "+
                (carRepository.getCarDbList(application.applicationContext).value?.size ?: null)
        )
        println("carRepository size carDbList: "+
                (carRepository.getCarDbList(application.applicationContext).value?.size ?: 0)
        )

        // If any transformation is needed, this can be simply done by Transformations class ...
        viewModelScope.launch {
            carListObservable = carRepository.carList()
        }
        viewModelScope.launch {
            try {
                carDbListObservable = carRepository.getCarDbList(application.applicationContext)
            } catch (e: Exception) {
                Log.e(TAG, "Something Went Wrong", e)
            }
        }
    }

    companion object {
        const val TAG = "CarListViewModel"
    }
}