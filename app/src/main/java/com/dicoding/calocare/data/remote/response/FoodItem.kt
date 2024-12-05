package com.dicoding.calocare.data.remote.response

data class FoodItem(
    val id: String,
    val foodName: String,
    val carbohydrate: Double,
    val protein: Double,
    val fat: Double,
    val calories: Int,
    val totalNutrition: Double,
    val evaluation: String?
)