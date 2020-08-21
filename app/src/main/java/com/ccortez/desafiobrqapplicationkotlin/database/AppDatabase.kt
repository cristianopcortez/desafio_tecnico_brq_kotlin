package com.ccortez.desafiobrqapplicationkotlin.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ccortez.desafiobrqapplicationkotlin.dao.CarDao
import com.ccortez.desafiobrqapplicationkotlin.service.model.Car

@Database(entities = [Car::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun carDao(): CarDao?

    companion object {
        private var INSTANCE: AppDatabase? = null
        @JvmStatic
        fun getAppDatabase(context: Context?): AppDatabase? {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context!!,
                    AppDatabase::class.java, "car-database"
                ) // allow queries on the main thread.
// Don't do this on a real app! See PersistenceBasicSample for an example.
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}