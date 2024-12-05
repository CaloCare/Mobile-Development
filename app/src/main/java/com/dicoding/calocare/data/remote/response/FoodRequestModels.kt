package com.dicoding.calocare.data.remote.response

import com.google.gson.annotations.SerializedName

data class AddFoodRequest(
    val food_name: String,
    val carbohydrate: Double,
    val protein: Double,
    val fat: Double,
    val calories: Double,
    val total_nutrition: Double,
    val evaluation: String
)

data class SearchFoodRequest(
    @SerializedName("name") val name: String
)

data class DeleteFoodRequest(
    @SerializedName("name") val name: String
)