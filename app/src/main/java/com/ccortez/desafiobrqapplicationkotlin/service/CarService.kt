package com.ccortez.desafiobrqapplicationkotlin.service

import android.content.Context
import com.ccortez.desafiobrqapplicationkotlin.database.AppDatabase
import com.ccortez.desafiobrqapplicationkotlin.database.AppDatabase.Companion.getAppDatabase

class CarService (var mContext: Context?) {

    var db: AppDatabase?

    constructor(mContext: Context?, db: AppDatabase?) : this(mContext) {
        this.db = db
    }

    val sumFromShopCart: Int
        get() {
            var total = 0
            val cars = db!!.carDao()!!.allCars
            for (i in cars!!.indices) {
                val car = cars[i]
                total = total + car!!.preco * car.quantidade
            }
            return total
        }

    init {
        db = getAppDatabase(mContext?.applicationContext)
    }
}