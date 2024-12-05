package com.dicoding.calocare.ui.form

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.calocare.data.repository.FoodRepository
import kotlinx.coroutines.launch
import com.dicoding.calocare.data.Result
import com.dicoding.calocare.data.remote.response.FoodItem
import com.dicoding.calocare.data.remote.response.FoodResponse

class FormViewModel(private val foodRepository: FoodRepository): ViewModel() {

    private val _addFoodResult = MutableLiveData<Result<FoodItem>>()
    val addFoodResult: LiveData<Result<FoodItem>> get() = _addFoodResult

    fun addNewFood(
        foodName: String,
        carbohydrate: Double,
        protein: Double,
        fat: Double,
        calories: Int,
        totalNutrition: Double,
        evaluation: String,
        onSuccess: (FoodItem) -> Unit
    ) {
        viewModelScope.launch {
            _addFoodResult.value = Result.Loading
            val result = try {
                foodRepository.addNewFood(
                    foodName, carbohydrate, protein, fat, calories, totalNutrition, evaluation
                )
            } catch (e: Exception) {
                Result.Error(e.message ?: "Unknown Error")
            }

            if (result is Result.Success) {
                val foodItem = FoodItem(
                    id = result.data.data.foodId,
                    foodName = foodName,
                    carbohydrate = carbohydrate,
                    protein = protein,
                    fat = fat,
                    calories = calories,
                    totalNutrition = totalNutrition,
                    evaluation = evaluation
                )
                onSuccess(foodItem)
            }
            _addFoodResult.value = mapResultToFoodItem(result, foodName)
        }
    }

    private fun mapResultToFoodItem(
        result: Result<FoodResponse>,
        foodName: String
    ): Result<FoodItem> {
        return when (result) {
            is Result.Success -> {
                val foodResponse = result.data.data
                Result.Success(
                    FoodItem(
                        id = foodResponse.foodId,
                        foodName = foodName,
                        carbohydrate = foodResponse.carbohydrate,
                        protein = foodResponse.protein,
                        fat = foodResponse.fat,
                        calories = foodResponse.calories,
                        totalNutrition = foodResponse.totalNutrition,
                        evaluation = foodResponse.evaluation ?: "Unknown"
                    )
                )
            }
            is Result.Error -> Result.Error(result.message ?: "Unknown error")
            is Result.Loading -> Result.Loading
        }
    }
}