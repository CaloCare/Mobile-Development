package com.dicoding.calocare.data.repository

import com.dicoding.calocare.data.Result
import com.dicoding.calocare.data.remote.response.*
import com.dicoding.calocare.data.remote.retrofit.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class FoodRepository private constructor(
    private val apiService: ApiService
) {
    companion object {
        @Volatile
        private var instance: FoodRepository? = null
        fun getInstance(apiService: ApiService): FoodRepository {
            return instance ?: synchronized(this) {
                instance ?: FoodRepository(apiService).also { instance = it }
            }
        }
    }

    suspend fun addNewFood(
        foodName: String,
        carbohydrate: Double,
        protein: Double,
        fat: Double,
        calories: Int,
        totalNutrition: Double,
        evaluation: String
    ): Result<FoodResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val foodRequest = Food(
                    foodName = foodName,
                    carbohydrate = carbohydrate,
                    protein = protein,
                    fat = fat,
                    calories = calories,
                    totalNutrition = totalNutrition,
                    evaluation = evaluation
                )

                val response = apiService.addFood(foodRequest)
                Result.Success(response)
            } catch (e: IOException) {
                Result.Error("Network error: ${e.message}")
            } catch (e: HttpException) {
                Result.Error("HTTP error: ${e.message}")
            } catch (e: Exception) {
                Result.Error("An unexpected error occurred: ${e.message}")
            }
        }
    }

    suspend fun getAllFoods(): Result<List<FoodItem>> {
        return withContext(Dispatchers.IO) {
            try {
                val response =
                    apiService.showAllFood()
                Result.Success(response)
            } catch (e: IOException) {
                Result.Error("Network error: ${e.message}")
            } catch (e: HttpException) {
                Result.Error("HTTP error: ${e.message}")
            } catch (e: Exception) {
                Result.Error("An unexpected error occurred: ${e.message}")
            }
        }
    }

    suspend fun searchFoodByName(foodName: String): Result<SearchResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val searchRequest = SearchRequest(name = foodName)
                val response =
                    apiService.searchFood(searchRequest)
                Result.Success(response)
            } catch (e: IOException) {
                Result.Error("Network error: ${e.message}")
            } catch (e: HttpException) {
                Result.Error("HTTP error: ${e.message}")
            } catch (e: Exception) {
                Result.Error("An unexpected error occurred: ${e.message}")
            }
        }
    }

    suspend fun deleteFoodByName(foodName: String): Result<DeleteResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val deleteRequest = DeleteRequest(name = foodName)
                val response = apiService.deleteFood(deleteRequest)
                Result.Success(response)
            } catch (e: IOException) {
                Result.Error("Network error: ${e.message}")
            } catch (e: HttpException) {
                Result.Error("HTTP error: ${e.message}")
            } catch (e: Exception) {
                Result.Error("An unexpected error occurred: ${e.message}")
            }
        }
    }
}