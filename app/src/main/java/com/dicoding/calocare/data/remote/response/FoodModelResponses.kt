package com.dicoding.calocare.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class FoodModelResponses(
    val status: String?, // For POST responses like Add Food
    val message: String?, // General success/error messages
    val data: FoodDataResponse?, // For Add Food or Search Food response
    val foods: List<Food>? = null // For Get All Food response
) : Parcelable

@Parcelize
data class FoodDataResponse(
    val foodId: String // For Add Food Response, assuming it should always be present
) : Parcelable

@Parcelize
data class Food(
    val id: String?, // For individual food entries
    val food_name: String,
    val carbohydrate: Double, // Amount of carbohydrates
    val protein: Double, // Amount of protein
    val fat: Double, // Amount of fat
    val calories: Double, // Total calories
    val total_nutrition: Double,
    val evaluation: String, // Evaluation message
    val createdAt: String? = null // Only for Search or Show All Food
) : Parcelable




//@Parcelize
//data class AddFoodResponse(
//    val status: String,
//    val message: String,
//    val data: FoodId
//) : Parcelable
//
//@Parcelize
//data class FoodId(
//    val foodId: String
//) : Parcelable
//
//@Parcelize
//data class ShowAllFoodResponse(
//    val foods: List<FoodModelResponses> // Changed to FoodModelResponses to match your original model
//) : Parcelable
//
//@Parcelize
//data class SearchFoodRequest(
//    val name: String
//) : Parcelable
//
//@Parcelize
//data class SearchFoodResponse(
//    val status: String,
//    val data: SearchData
//) : Parcelable
//
//@Parcelize
//data class SearchData(
//    val foods: List<FoodDetails>
//) : Parcelable
//
//@Parcelize
//data class FoodDetails(
//    val id: String,
//    val foodName: String,
//    val carbohydrate: Double,
//    val protein: Double,
//    val fat: Double,
//    val calories: Double,
//    val totalNutrition: Double,
//    val evaluation: String,
//) : Parcelable
//
//@Parcelize
//data class DeleteFoodResponse(
//    val message: String
//) : Parcelable
