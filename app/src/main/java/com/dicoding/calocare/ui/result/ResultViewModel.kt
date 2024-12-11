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

    private val _foodDeleted = MutableLiveData<Result<Boolean>>()
    val foodDeleted: LiveData<Result<Boolean>> get() = _foodDeleted


    fun setFoodResult(foodItem: FoodItem) {
        _foodResult.value = foodItem
        Log.d("ResultViewModel", "Food item set: ${foodItem.foodName}")
    }

    fun deleteFoodByName(foodName: String?) {
        viewModelScope.launch {
            if (foodName.isNullOrBlank()) {
                _foodDeleted.value = Result.Error("Nama makanan tidak boleh kosong")
                return@launch
            }

            _foodDeleted.value = Result.Loading

            try {
                val result = foodRepository.deleteFoodByName(foodName.trim())
                _foodDeleted.value = result
            } catch (e: Exception) {
                Log.e("DeleteResult", "Deletion failed", e)
                _foodDeleted.value = Result.Error(e.message ?: "Gagal menghapus makanan")
            }
        }
    }
}