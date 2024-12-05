package com.dicoding.calocare.data.remote.response

data class FoodResponse(
    val status: String,
    val message: String,
    val data: FoodItemResponse
)

data class FoodItemResponse(
    val foodId: String,
    val foodName: String,
    val carbohydrate: Double,
    val protein: Double,
    val fat: Double,
    val calories: Int,
    val totalNutrition: Double,
    val evaluation: String
)