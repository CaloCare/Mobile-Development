package com.dicoding.calocare.data.remote.response

data class FoodResponse(
    val status: String,
    val message: String? = null,
    val data: FoodData
)

data class FoodData(
    val foods: List<FoodItem>
)