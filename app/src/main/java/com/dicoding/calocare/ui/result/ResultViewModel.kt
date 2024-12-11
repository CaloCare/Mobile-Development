package com.dicoding.calocare.ui.result

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.calocare.data.remote.response.FoodItem
import com.dicoding.calocare.data.repository.FoodRepository
import kotlinx.coroutines.launch
import com.dicoding.calocare.data.Result

class ResultViewModel(private val foodRepository: FoodRepository) : ViewModel() {

    private val _foodResult = MutableLiveData<FoodItem>()
    val foodResult: LiveData<FoodItem> get() = _foodResult

    private val _foodDeleted = MutableLiveData<Boolean>()
    val foodDeleted: LiveData<Boolean> get() = _foodDeleted


    fun setFoodResult(foodItem: FoodItem) {
        _foodResult.value = foodItem
    }

    fun deleteFoodByName(foodName: String) {
        viewModelScope.launch {
            val result = foodRepository.deleteFoodByName(foodName)
            when (result) {
                is Result.Success -> Log.d("DeleteResult", "Deletion successful: ${result.data}")
                is Result.Error -> Log.e("DeleteResult", "Deletion failed: ${result.message}")
                Result.Loading -> TODO()
            }
        }
    }
}