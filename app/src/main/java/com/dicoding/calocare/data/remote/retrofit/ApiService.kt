package com.dicoding.calocare.data.remote.retrofit

import com.dicoding.calocare.data.remote.response.AddFoodRequest
import com.dicoding.calocare.data.remote.response.DeleteFoodRequest
import com.dicoding.calocare.data.remote.response.Food
import com.dicoding.calocare.data.remote.response.FoodModelResponses
import com.dicoding.calocare.data.remote.response.SearchFoodRequest
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {

    // Add New Food
    @POST("food")
    suspend fun addNewFood(
        @Body food: AddFoodRequest
    ): FoodModelResponses

    // Show All Food
    @GET("food")
    suspend fun showAllFood(): List<Food>

    // Search Food by Name
    @POST("food/search")
    suspend fun searchFood(
        @Body request: SearchFoodRequest
    ): FoodModelResponses

    // Delete Food by Name
    @DELETE("food/delete-by-name")
    suspend fun deleteFood(
        @Body request: DeleteFoodRequest
    ): FoodModelResponses
}