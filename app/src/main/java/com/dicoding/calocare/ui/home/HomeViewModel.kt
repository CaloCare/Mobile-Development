package com.dicoding.calocare.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.calocare.data.remote.response.FoodItem
import com.dicoding.calocare.data.repository.FoodRepository
import kotlinx.coroutines.launch
import com.dicoding.calocare.data.Result

class HomeViewModel(private val foodRepository: FoodRepository) : ViewModel() {

    private val _foodList = MutableLiveData<List<FoodItem>>()
    val foodList: LiveData<List<FoodItem>> get() = _foodList

    fun getAllFood() {
        viewModelScope.launch {
            val result = try {
                foodRepository.getAllFoods()
            } catch (e: Exception) {
                Result.Error(e.message ?: "Failed to fetch foods")
            }
        }
    }
}