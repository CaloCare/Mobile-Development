package com.dicoding.calocare.di

import android.content.Context
import com.dicoding.calocare.data.remote.retrofit.ApiConfig
import com.dicoding.calocare.data.repository.FoodRepository

object Injection {
    fun foodRepository(context: Context): FoodRepository {
        val apiService = ApiConfig.getApiService()
        return FoodRepository.getInstance(apiService)
    }
}