package com.ccortez.desafiobrqapplicationkotlin.viewmodel

import android.app.Application
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.*
import com.ccortez.desafiobrqapplicationkotlin.service.model.Car
import com.ccortez.desafiobrqapplicationkotlin.service.repository.CarRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class CarViewModel @Inject constructor(
    carRepository: CarRepository,
    application: Application
) : AndroidViewModel(application) {

    lateinit var observableCar: LiveData<Car>
    lateinit var carID: MutableLiveData<String>
    @JvmField
    var car = ObservableField<Car>()

    fun setCar(car: Car) {
        this.car.set(car)
    }

    fun setCarID(carID: String?) {
        this.carID.value = carID
    }

    companion object {
        private val TAG = CarViewModel::class.java.name
        private val ABSENT: MutableLiveData<*> = MutableLiveData<Any?>()
    }

    init {
        ABSENT.value = null
    }

    init {
        viewModelScope.launch {
            carID = MutableLiveData()
            observableCar = Transformations.switchMap<String, Car>(
                carID
            ) { input: String ->
                if (input.isEmpty()) {
                    Log.i(
                        TAG,
                        "CarViewModel carID is absent!!!"
                    )
//                return@switchMap ABSENT
                }
                Log.i(
                    TAG,
                    "CarViewModel carID is " + carID.value
                )
                getCarDetails(carRepository)
            }
        }
    }

    fun getCarDetails(carRepository: CarRepository) : LiveData<Car?> {
        return carRepository.getCarDetails(carID.value)
    }
}