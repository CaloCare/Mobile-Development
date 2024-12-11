package com.dicoding.calocare.data.remote.retrofit

import com.dicoding.calocare.data.remote.response.DeleteRequest
import com.dicoding.calocare.data.remote.response.Food
import com.dicoding.calocare.data.remote.response.FoodResponse
import com.dicoding.calocare.data.remote.response.SearchRequest
import com.dicoding.calocare.data.remote.response.SearchResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST

interface ApiService {

    @POST("/food")
    suspend fun addFood(@Body food: Food): FoodResponse

    @GET("/food")
    suspend fun showAllFood(): FoodResponse

    @POST("/food/search")
    suspend fun searchFood(@Body request: SearchRequest): SearchResponse

    @HTTP(method = "DELETE", path = "food/delete-by-name", hasBody = true)
    suspend fun deleteFood(
        @Body request: DeleteRequest
    ): Boolean
}