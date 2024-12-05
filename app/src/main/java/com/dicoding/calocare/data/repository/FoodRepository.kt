package com.dicoding.calocare.data.repository

import com.dicoding.calocare.data.remote.retrofit.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import com.dicoding.calocare.data.Result
import com.dicoding.calocare.data.remote.response.AddFoodRequest
import com.dicoding.calocare.data.remote.response.FoodModelResponses
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
        food_name: String,
        carbohydrate: Double,
        protein: Double,
        fat: Double,
        calories: Double,
        total_nutrition: Double,
        evaluation: String
    ): Result<FoodModelResponses> {
        return withContext(Dispatchers.IO) {
            try {
                // Create AddFoodRequest
                val addFoodRequest = AddFoodRequest(
                    food_name = food_name,
                    carbohydrate = carbohydrate,
                    protein = protein,
                    fat = fat,
                    calories = calories,
                    total_nutrition = total_nutrition,
                    evaluation = evaluation
                )

                // Call the API
                val response = apiService.addNewFood(addFoodRequest)

                // Return success
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
