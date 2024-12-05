package com.dicoding.calocare.ui.result

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.calocare.data.remote.response.FoodItem
import com.dicoding.calocare.data.repository.FoodRepository

class ResultViewModel(private val foodRepository: FoodRepository) : ViewModel() {

    private val _foodResult = MutableLiveData<FoodItem>()
    val foodResult: LiveData<FoodItem> get() = _foodResult

    fun setFoodResult(foodItem: FoodItem) {
        _foodResult.value = foodItem
    }
}