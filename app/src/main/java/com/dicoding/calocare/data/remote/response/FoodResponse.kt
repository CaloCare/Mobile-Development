package com.dicoding.calocare.data.remote.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class FoodResponse(
    val listStory: List<ListFoodItem>,
    val error: Boolean,
    val message: String
)

@Parcelize
data class ListFoodItem(
    val  foodName: String,
    val  carbohydrate: String,
    val  protein: String,
    val  fat: String,
    val  calories: String,

    val date: String,

    val result: String
) : Parcelable