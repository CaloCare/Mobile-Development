package com.dicoding.calocare.data.remote.response

import com.google.gson.annotations.SerializedName

data class FoodDetailResponse(
    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("story")
    val story: ListFoodItem
)

//data class Food(
//    @field:SerializedName("foodName")
//    val foodName: String,
//
//    @field:SerializedName("carbohydrate")
//    val carbohydrate: String,
//
//    @field:SerializedName("protein")
//    val protein: String,
//
//    @field:SerializedName("fat")
//    val fat: String,
//
//    @field:SerializedName("calories")
//    val calories: String,
//
//    @field:SerializedName("date")
//    val date: String,
//
//    @field:SerializedName("result")
//    val result: String
//
//)




