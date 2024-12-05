package com.dicoding.calocare.data.remote.retrofit

import com.dicoding.calocare.data.remote.response.DeleteRequest
import com.dicoding.calocare.data.remote.response.DeleteResponse
import com.dicoding.calocare.data.remote.response.Food
import com.dicoding.calocare.data.remote.response.FoodItem
import com.dicoding.calocare.data.remote.response.FoodResponse
import com.dicoding.calocare.data.remote.response.SearchRequest
import com.dicoding.calocare.data.remote.response.SearchResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @POST("/food")
    suspend fun addFood(@Body food: Food): FoodResponse

    @GET("/food")
    suspend fun showAllFood(): List<FoodItem>

    @POST("/food/search")
    suspend fun searchFood(@Body request: SearchRequest): SearchResponse

    @DELETE("/food/delete-by-name")
    suspend fun deleteFood(@Body request: DeleteRequest): DeleteResponse
}