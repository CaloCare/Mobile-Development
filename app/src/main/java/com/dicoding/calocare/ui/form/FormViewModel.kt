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
            val result = foodRepository.addNewFood(
                foodName, carbohydrate, protein, fat, calories, totalNutrition, evaluation
            )

            when (result) {
                is Result.Success -> {
                    val newFoodItem = FoodItem(
                        id = "",
                        foodName = foodName,
                        carbohydrate = carbohydrate,
                        protein = protein,
                        fat = fat,
                        calories = calories,
                        totalNutrition = totalNutrition,
                        evaluation = evaluation
                    )
                    onSuccess(newFoodItem)
                    _addFoodResult.value = Result.Success(newFoodItem)
                }
                is Result.Error -> {
                    _addFoodResult.value = Result.Error(result.message)
                }
                is Result.Loading -> {
                    _addFoodResult.value = Result.Loading
                }
            }
        }
    }
}