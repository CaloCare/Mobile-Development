package com.dicoding.calocare.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.calocare.data.remote.response.FoodItem
import com.dicoding.calocare.data.repository.FoodRepository
import kotlinx.coroutines.launch
import com.dicoding.calocare.data.Result

class HomeViewModel(private val foodRepository: FoodRepository) : ViewModel() {

    private val _foodItems = MutableLiveData<List<FoodItem>>()
    val foodItem: LiveData<List<FoodItem>> get() = _foodItems

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _foods = MutableLiveData<List<FoodItem>>()
    val foods: LiveData<List<FoodItem>> = _foods

    fun getAllFood() {
        viewModelScope.launch {
            when (val result = foodRepository.getAllFoods()) {
                is Result.Success -> {
                    _foodItems.value = result.data
                }
                is Result.Error -> {
                    Log.e("HomeViewModel", "Failed to fetch foods: ${result.message}")
                    _errorMessage.value = result.message
                }
                Result.Loading -> TODO()
            }
        }
    }

    fun fetchAllFoods() {
        viewModelScope.launch {
            val result = foodRepository.getAllFoods()
            when (result) {
                is Result.Success -> {
                    _foods.value = result.data
                }
                is Result.Error -> {
                    Log.e("HomeViewModel", "Error fetching foods: ${result.message}")
                }
                Result.Loading -> {
                    // Handle loading state
                }
            }
        }
    }
}