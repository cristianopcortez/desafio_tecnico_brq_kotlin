package com.ccortez.desafiobrqapplicationkotlin.view.callback

import com.ccortez.desafiobrqapplicationkotlin.service.model.Car

interface ShopCartClickCallback {
    fun onClick(car: Car?)
    fun onClickRemoveFromCart(car: Car?)
}