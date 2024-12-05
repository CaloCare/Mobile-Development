package com.dicoding.calocare.data.remote.response

data class SearchResponse(
    val status: String,
    val data: SearchData
)

data class SearchData(
    val foods: List<FoodSearchResult>
)

data class FoodSearchResult(
    val id: String,
    val foodName: String,
    val carbohydrate: Double,
    val protein: Double,
    val fat: Double,
    val calories: Int,
    val totalNutrition: Double,
    val evaluation: String,
    val createdAt: String
)