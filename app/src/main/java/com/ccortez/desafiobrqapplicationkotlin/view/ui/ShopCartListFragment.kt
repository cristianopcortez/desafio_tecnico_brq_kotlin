package com.ccortez.desafiobrqapplicationkotlin.view.ui

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.ccortez.desafiobrqapplicationkotlin.R
import com.ccortez.desafiobrqapplicationkotlin.database.AppDatabase
import com.ccortez.desafiobrqapplicationkotlin.databinding.FragmentShopCartListBinding
import com.ccortez.desafiobrqapplicationkotlin.di.Injectable
import com.ccortez.desafiobrqapplicationkotlin.service.model.Car
import com.ccortez.desafiobrqapplicationkotlin.view.adapter.ShopCartAdapter
import com.ccortez.desafiobrqapplicationkotlin.view.callback.ShopCartClickCallback
import com.ccortez.desafiobrqapplicationkotlin.viewmodel.CarListViewModel
import dagger.android.support.AndroidSupportInjection
import java.util.*
import javax.inject.Inject

class ShopCartListFragment : Fragment(), Injectable {
    private lateinit var shopCartAdapter: ShopCartAdapter
    private lateinit var binding: FragmentShopCartListBinding
    var db: AppDatabase? = null
    var mContext: Context? = null
    var mActionMode: ActionMode? = null
    var btnFinalizarCompra: ConstraintLayout? = null
    var _currentFragment = this

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_shop_cart_list,
            container, false
        )
        shopCartAdapter = ShopCartAdapter(shopCartClickCallback)
        binding!!.shopCarList.setAdapter(shopCartAdapter)
        binding!!.setIsLoading(true)
        mContext = activity!!.getApplicationContext()
        db = AppDatabase.getAppDatabase(mContext)
        mActionMode =
            activity!!.startActionMode(mActionModeCallback)
        btnFinalizarCompra = binding!!.btnFinalizarCompra
        btnFinalizarCompra!!.setOnClickListener {
            //            db.carDao().clear();
            if (activity != null) activity!!.finish()
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
        viewModel.getCarDbListObservable(mContext)
            .observe(this, Observer<List<Car>> { cars ->
                if (cars != null) {
                    binding!!.setIsLoading(false)
                    shopCartAdapter!!.setCarList(cars)
                }
            })
    }

    private val shopCartClickCallback: ShopCartClickCallback = object : ShopCartClickCallback {
        override fun onClick(car: Car?) {
            if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                if (car != null) {
                    (activity as MainActivity?)!!.show(car)
                }
            }
        }

        override fun onClickRemoveFromCart(car: Car?) {
            if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {

                val viewModel = ViewModelProviders.of(
                    _currentFragment,
                    viewModelFactory
                ).get(CarListViewModel::class.java)
                viewModel.removeFromCart(car)

                shopCartAdapter!!.setCarList(db!!.carDao()!!.allCars)
                shopCartAdapter!!.notifyDataSetChanged()
                if (shopCartAdapter!!.itemCount == 0) {
                    Toast.makeText(
                        activity!!.applicationContext,
                        "Carrinho vazio !",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
    private val mActionModeCallback: ActionMode.Callback =
        object : ActionMode.Callback {
            override fun onCreateActionMode(
                mode: ActionMode,
                menu: Menu
            ): Boolean { // Inflate a menu resource providing context menu items
                val inflater = mode.menuInflater
                for (i in 0 until menu.size()) {
                    menu.getItem(i).setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER)
                }
                inflater.inflate(R.menu.menu_no_items, menu)
                return true
            }

            override fun onPrepareActionMode(
                mode: ActionMode,
                menu: Menu
            ): Boolean {
                return true
            }

            override fun onActionItemClicked(
                mode: ActionMode,
                item: MenuItem
            ): Boolean {
                return when (item.itemId) {
                    else -> false
                }
            }

            override fun onDestroyActionMode(mode: ActionMode) {
                mActionMode = null
                if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                    (activity as MainActivity?)!!.showHome()
                }
            }
        }

    companion object {
        const val TAG = "ShopCartListFragment"
    }
}