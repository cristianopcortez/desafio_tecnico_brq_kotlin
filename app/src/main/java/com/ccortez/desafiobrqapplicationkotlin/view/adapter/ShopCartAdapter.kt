package com.ccortez.desafiobrqapplicationkotlin.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ccortez.desafiobrqapplicationkotlin.R
import com.ccortez.desafiobrqapplicationkotlin.databinding.ShopCartListItemBinding
import com.ccortez.desafiobrqapplicationkotlin.service.model.Car
import com.ccortez.desafiobrqapplicationkotlin.view.callback.ShopCartClickCallback
import com.squareup.picasso.Picasso

class ShopCartAdapter(private val shopCartClickCallback: ShopCartClickCallback?) :
    RecyclerView.Adapter<ShopCartAdapter.CarViewHolder>() {

    internal var carList: List<Car>? = null

    fun setCarList(carList: List<Car>) {
        if (this.carList == null) {
            this.carList = carList
            notifyItemRangeInserted(0, carList.size)
        } else {
            val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun getOldListSize(): Int {
                    return this@ShopCartAdapter.carList!!.size
                }

                override fun getNewListSize(): Int {
                    return carList.size
                }

                override fun areItemsTheSame(
                    oldItemPosition: Int,
                    newItemPosition: Int
                ): Boolean {
                    return this@ShopCartAdapter.carList!![oldItemPosition].id ==
                            carList[newItemPosition].id
                }

                override fun areContentsTheSame(
                    oldItemPosition: Int,
                    newItemPosition: Int
                ): Boolean {
                    if (carList.size > oldItemPosition) {
                        val (id) = carList[newItemPosition]
                        val (id1) = carList[oldItemPosition]
                        return id == id1
                    }
                    return false
                }
            })
            this.carList = carList
            result.dispatchUpdatesTo(this)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CarViewHolder {
        val binding: ShopCartListItemBinding = DataBindingUtil
            .inflate(
                LayoutInflater.from(parent.context),
                R.layout.shop_cart_list_item,
                parent, false
            )
        binding.setCallback(shopCartClickCallback)
        return CarViewHolder(
            binding
        )
    }

    override fun onBindViewHolder(
        holder: CarViewHolder,
        position: Int
    ) {
        holder.binding.setCar(carList!![position])
        // Log.d(TAG, "img: "+carList.get(position).imagem);
        Log.d(TAG, "car: " + carList!![position])
        Picasso.get()
            .load(carList!![position].imagem)
            .placeholder(R.drawable.ic_car)
            .error(R.drawable.ic_alert)
            .into(holder.carImage)
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return if (carList == null) 0 else carList!!.size
    }

    class CarViewHolder(binding: ShopCartListItemBinding) :
        RecyclerView.ViewHolder(binding.getRoot()) {
        val binding: ShopCartListItemBinding
        val carImage: ImageView

        init {
            this.binding = binding
            carImage = binding.carImage
        }
    }

    companion object {
        private val TAG = ShopCartAdapter::class.java.simpleName
    }

}