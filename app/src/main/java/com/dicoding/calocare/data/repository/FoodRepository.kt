package com.dicoding.calocare.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.dicoding.calocare.data.Result
import com.dicoding.calocare.data.remote.response.*
import com.dicoding.calocare.data.remote.retrofit.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException

class FoodRepository(private val apiService: ApiService) {
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
                val response = apiService.showAllFood()
                Result.Success(response.data.foods)
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

    suspend fun deleteFoodByName(foodName: String): Result<Boolean> {
        return withContext(Dispatchers.IO) {
            try {
                if (foodName.isNullOrBlank()) {
                    Log.e("DeleteRepository", "Food name is null or blank")
                    return@withContext Result.Error("Nama makanan tidak boleh kosong")
                }

                Log.d("DeleteRepository", "Deleting food: $foodName")
                val deleteRequest = DeleteRequest(name = foodName.trim())
                Log.d("DeleteRepository", "Delete Request: $deleteRequest")

                val response = apiService.deleteFood(deleteRequest)

                if (response) {
                    Log.d("DeleteRepository", "Food deleted successfully")
                    Result.Success(true)
                } else {
                    Log.e("DeleteRepository", "Failed to delete food")
                    Result.Error("Gagal menghapus makanan")
                }
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                Log.e("DeleteRepository", "HTTP error: ${e.code()}")
                Log.e("DeleteRepository", "Error body: $errorBody")

                val errorMessage = try {
                    val errorJson = JSONObject(errorBody ?: "{}")
                    errorJson.optString("message", "Gagal menghapus makanan")
                } catch (jsonEx: Exception) {
                    "Gagal menghapus makanan: ${e.message()}"
                }

                Result.Error(errorMessage)
            } catch (e: Exception) {
                Log.e("DeleteRepository", "Exception during deletion", e)
                Result.Error(e.message ?: "Terjadi kesalahan tidak terduga")
            }
        }
    }
}