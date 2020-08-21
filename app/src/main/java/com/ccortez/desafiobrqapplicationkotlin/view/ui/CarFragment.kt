package com.ccortez.desafiobrqapplicationkotlin.view.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.util.Pair
import android.view.*
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.ccortez.desafiobrqapplicationkotlin.R
import com.ccortez.desafiobrqapplicationkotlin.database.AppDatabase
import com.ccortez.desafiobrqapplicationkotlin.database.AppDatabase.Companion.getAppDatabase
import com.ccortez.desafiobrqapplicationkotlin.databinding.FragmentCarDetailsBinding
import com.ccortez.desafiobrqapplicationkotlin.di.Injectable
import com.ccortez.desafiobrqapplicationkotlin.service.CarService
import com.ccortez.desafiobrqapplicationkotlin.viewmodel.CarViewModel
import com.squareup.picasso.Picasso
import dagger.android.support.AndroidSupportInjection
import java.util.*
import javax.inject.Inject

class CarFragment : Fragment(), Injectable {

    private lateinit var binding: FragmentCarDetailsBinding
    var mActionMode: ActionMode? = null
    var db: AppDatabase? = null
    var carService: CarService? = null

    var viewModelFactory: ViewModelProvider.Factory? = null
        @Inject set

    var qtdList: MutableList<Pair<String, String>>? = null
    private var mContext: Context? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? { // Inflate this data binding layout
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_car_details, container, false)
        mActionMode =
            activity?.startActionMode(mActionModeCallback)
        mContext = activity?.applicationContext
        db = getAppDatabase(mContext?.getApplicationContext())
        carService = CarService(mContext)
        // Create and set the adapter for the RecyclerView.
        return binding.getRoot()
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

        val viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(CarViewModel::class.java)

        viewModel.setCarID(arguments!!.getString(KEY_CAR_ID))
        binding!!.carViewModel = viewModel
        binding!!.isLoading = true
        observeViewModel(viewModel)
    }

    private fun observeViewModel(viewModel: CarViewModel) { // Observe car data
        viewModel.observableCar.observe(this, Observer { car ->
            if (car != null) {
                binding!!.isLoading = false
                viewModel.setCar(car)
                Picasso.get()
                    .load(car.imagem)
                    .placeholder(R.drawable.ic_car)
                    .error(R.drawable.ic_alert)
                    .into(binding!!.imageView)
                binding!!.btnColocarCarrinho.setOnClickListener {
                    Log.d(
                        TAG,
                        "QTD SELECTED: " + binding!!.spinnerQtd.selectedItem
                    )
                    Log.d(
                        TAG,
                        "QTD TOTAL: " + car.quantidade
                    )
                    if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                        val carToVerify = db!!.carDao()!!.getCarById(car.id)
                        var intCarQty =
                            Integer.valueOf(binding!!.spinnerQtd.selectedItem.toString())
                        if (carToVerify == null) {
                            if (car.preco * intCarQty + carService!!.sumFromShopCart <= 100000) {
                                car.quantidade = intCarQty
                                db!!.carDao()!!.insert(car)
                                //                                        for (int i = 0; i < intCarQty; i++) {
//                                            db.carDao().insert(car);
//                                        }
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
                        } else {
                            if (car.preco * intCarQty + carService!!.sumFromShopCart <= 100000) {
                                if (carToVerify.quantidade + intCarQty <= car.quantidade) {
                                    Toast.makeText(
                                        activity!!.applicationContext,
                                        "Carro já no carrinho ! Atualizando quantidade.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    intCarQty = intCarQty + carToVerify.quantidade
                                    carToVerify.quantidade = intCarQty
                                    db!!.carDao()!!.update(carToVerify)
                                } else {
                                    Toast.makeText(
                                        activity!!.applicationContext,
                                        "Não há carros disponíveis em estoque para colocar no carrinho!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            } else {
                                Toast.makeText(
                                    activity!!.applicationContext,
                                    "Carrinho passará de 100.000 em compras !",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
                addItemsOnSpinner(binding!!.spinnerQtd, car.quantidade)
            }
        })
    }

    fun addItemsOnSpinner(spin: Spinner, qtd: Int) {
        mContext = activity?.applicationContext
        //        db = AppDatabase.getAppDatabase(mContext.getApplicationContext());
//        spin = view.findViewById(R.id.spinner_gerir);
        val list: MutableList<String> =
            ArrayList()
        qtdList = ArrayList()
        for (i in 1 until qtd + 1) {
            (qtdList as ArrayList<Pair<String, String>>).add(Pair(i.toString(), i.toString()))
            //            eventsList.add(new Pair<>(lista_enderecos.get(i).getNomeDivulgacao(), lista_enderecos.get(i).getCodigo()));
//            list.add(lista_enderecos.get(i).getNomeDivulgacao());
            list.add(i.toString())
        }
        val dataAdapter = ArrayAdapter(
            activity!!.applicationContext,
            android.R.layout.simple_spinner_item,
            list
        )
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spin.adapter = dataAdapter
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
            ): Boolean { // ver onPositiveClick para ação de cada um
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
        const val TAG = "CarFragment"
        private const val KEY_CAR_ID = "car_id"
        /**
         * Creates car fragment for specific car ID
         */
        fun forCar(carID: String?): CarFragment {
            val fragment = CarFragment()
            val args = Bundle()
            args.putString(KEY_CAR_ID, carID)
            fragment.arguments = args
            return fragment
        }
    }
}