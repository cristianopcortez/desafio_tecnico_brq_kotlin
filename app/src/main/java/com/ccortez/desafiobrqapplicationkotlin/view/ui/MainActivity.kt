package com.ccortez.desafiobrqapplicationkotlin.view.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.ccortez.desafiobrqapplicationkotlin.R
import com.ccortez.desafiobrqapplicationkotlin.service.model.Car
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.DaggerAppCompatActivity
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Add car list fragment if this is first creation
        if (savedInstanceState == null) {
            val fragment = CarListFragment()
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, fragment, CarListFragment.TAG).commit()
        }
    }

    fun show(car: Car) {
        val carFragment = CarFragment.forCar(car.id.toString())
        supportFragmentManager
            .beginTransaction()
            .addToBackStack("car")
            .replace(
                R.id.fragment_container,
                carFragment, CarFragment.TAG
            ).commit()
    }

    fun showHome() {
        val carListFragment = CarListFragment()
        supportFragmentManager
            .beginTransaction()
            .addToBackStack("carlist")
            .replace(
                R.id.fragment_container,
                carListFragment, CarListFragment.TAG
            ).commit()
    }

    fun showCart() {
        val shopCartListFragment = ShopCartListFragment()
        supportFragmentManager
            .beginTransaction()
            .addToBackStack("shopcart")
            .replace(
                R.id.fragment_container,
                shopCartListFragment, ShopCartListFragment.TAG
            ).commit()
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return dispatchingAndroidInjector
    }
}