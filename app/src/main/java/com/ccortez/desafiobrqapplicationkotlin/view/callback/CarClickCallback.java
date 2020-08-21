package com.ccortez.desafiobrqapplicationkotlin.view.callback;

import com.ccortez.desafiobrqapplicationkotlin.service.model.Car;

public interface CarClickCallback {
    void onClick(Car car);
    void onClickPutInCart(Car car);
}
