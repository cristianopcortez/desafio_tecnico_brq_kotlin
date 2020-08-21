package com.ccortez.desafiobrqapplicationkotlin.view.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.ccortez.desafiobrqapplicationkotlin.R
import com.ccortez.desafiobrqapplicationkotlin.database.AppDatabase
import com.ccortez.desafiobrqapplicationkotlin.databinding.FragmentCarListBinding
import com.ccortez.desafiobrqapplicationkotlin.di.Injectable
import com.ccortez.desafiobrqapplicationkotlin.service.CarService
import com.ccortez.desafiobrqapplicationkotlin.service.model.Car
import com.ccortez.desafiobrqapplicationkotlin.view.adapter.CarAdapter
import com.ccortez.desafiobrqapplicationkotlin.view.callback.CarClickCallback
import com.ccortez.desafiobrqapplicationkotlin.viewmodel.CarListViewModel
import dagger.android.support.AndroidSupportInjection
import java.util.*
import javax.inject.Inject

class CarListFragment : Fragment(), Injectable {

    private lateinit var carAdapter: CarAdapter
    private lateinit var binding: FragmentCarListBinding
    var db: AppDatabase? = null
    var mContext: Context? = null
    var carService: CarService? = null

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_car_list, container, false)
        carAdapter = CarAdapter(carClickCallback)
        binding!!.carList.adapter = carAdapter
        binding!!.setIsLoading(true)
        mContext = activity!!.getApplicationContext()
        db = AppDatabase.getAppDatabase(mContext)
        carService = CarService(mContext)
        binding!!.fab.setOnClickListener {
            if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                (activity as MainActivity?)!!.showCart()
            }
        }
        return binding!!.getRoot()
    }

    override fun onAttach(context: Context) {
        // you should create a `DaggerAppComponent` instance once, e.g. in a custom `Application` class and use it throughout all activities and fragments
//        (context.applicationContext as MVVMApplication).appComponent.inject(this)
//        DaggerAppComponent.builder()
//            .application(context.applicationContext as MVVMApplication)
//            .build().inject(this)
//        (context.applicationContext as MVVMApplication)
//            .hotelListFragmentInjector(this)
        AndroidSupportInjection.inject(this)
//        DaggerAppComponent.builder()
//            .application(context.applicationContext as MVVMApplication).build()
//            .inject()
        super.onAttach(context)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val viewModel = ViewModelProviders.of(
            this,
            viewModelFactory
        ).get(CarListViewModel::class.java)
        observeViewModel(viewModel)
    }

    private fun observeViewModel(viewModel: CarListViewModel) { // Update the list when the data changes
        viewModel.carListObservable
            .observe(this, Observer { cars ->
                if (cars != null) {
                    binding!!.isLoading = false
                    carAdapter!!.setCarList(cars)
                }
            })
    }

    private val carClickCallback: CarClickCallback = object : CarClickCallback {
        override fun onClick(car: Car) {
            if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                (activity as MainActivity).show(car)
            }
        }

        override fun onClickPutInCart(car: Car) {
            if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                val carToVerify = db!!.carDao()?.getCarById(car.id)
                if (carToVerify == null) {
                    if (car.preco + carService!!.sumFromShopCart <= 100000) {
                        car.quantidade = 1
                        db!!.carDao()?.insert(car)
                        Toast.makeText(
                            activity!!.applicationContext,
                            "Adicionado ao carrinho !",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else Toast.makeText(
                        activity!!.applicationContext,
                        "Carrinho passará de 100.000 em compras !",
                        Toast.LENGTH_SHORT
                    ).show()
                } else Toast.makeText(
                    activity!!.applicationContext,
                    "Carro já no carrinho !",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    companion object {
        const val TAG = "CarListFragment"
    }
}