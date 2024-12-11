//package com.dicoding.calocare.data.local
//
//import androidx.lifecycle.LiveData
//import androidx.room.*
//
//@Dao
//interface FoodDao {
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertFood(food: FoodEntity)
//
//    @Query("SELECT * FROM food_table")
//    fun getAllFoods(): LiveData<List<FoodEntity>>
//
//    @Delete
//    suspend fun deleteFood(food: FoodEntity)
//}