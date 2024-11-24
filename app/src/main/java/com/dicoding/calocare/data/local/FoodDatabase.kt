package com.dicoding.calocare.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room

//@Database(
//    entities =
//        version =
//            exportSchema =
//)

abstract class FoodDatabase {
    abstract fun foodDao(): FoodDao

//    companion object{
//        @Volatile
//        private var INSTANCE: FoodDatabase? = null
//
//        @JvmStatic
//        fun getDatabase(context: Context): FoodDatabase {
//            return INSTANCE ?: synchronized(this) {
//                INSTANCE ?: Room.databaseBuilder(
//                    context.applicationContext,
//                    FoodDatabase::class.java, "food_database"
//                )
//            }
//                .fallbackToDestructiveMigration()
//                .build()
//                .also { INSTANCE = it }
//        }
//    }
}