package com.dicoding.calocare.ui.form

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.calocare.data.repository.FoodRepository
import kotlinx.coroutines.launch
import com.dicoding.calocare.data.Result
import com.dicoding.calocare.data.remote.response.FoodModelResponses

class FormViewModel(private val foodRepository: FoodRepository): ViewModel() {

    private val _addFoodResult = MutableLiveData<Result<FoodModelResponses>>()
    val addFoodResult: LiveData<Result<FoodModelResponses>> get() = _addFoodResult

    fun addNewFood(
        food_name: String,
        carbohydrate: Double,
        protein: Double,
        fat: Double,
        calories: Double,
        total_nutrition: Double,
        evaluation: String
    ) {
        viewModelScope.launch {
            val result =
                foodRepository.addNewFood(food_name, carbohydrate, protein, fat, calories, total_nutrition, evaluation)
        }
    }
}