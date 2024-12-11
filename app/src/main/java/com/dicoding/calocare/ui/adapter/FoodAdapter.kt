package com.dicoding.calocare.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.calocare.R
import com.dicoding.calocare.data.remote.response.FoodItem
import com.dicoding.calocare.databinding.ItemFoodBinding

class FoodAdapter(
    private var foodList: List<FoodItem>,
    private val onItemClick: (FoodItem) -> Unit
) : RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    private val descriptions = mapOf(
        5 to "Bad for you",
        4 to "Not very good for you",
        3 to "Fairly good for you",
        2 to "Good for you",
        1 to "Very good for you"
    )

    inner class FoodViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemFoodBinding.bind(view)

        fun bind(food: FoodItem) {
            val evaluationScore = food.evaluation?.toIntOrNull() ?: 0
            val evaluationDescription = descriptions[evaluationScore] ?: "No description available"

            binding.textViewFoodName.text = food.foodName
            binding.textViewCalories.text = "${food.calories.toString()} Calories"
            binding.textViewResult.text = "$evaluationScore ($evaluationDescription)"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_food, parent, false)
        return FoodViewHolder(view)
    }

    override fun getItemCount(): Int = foodList.size

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val food = foodList[position]
        holder.bind(food)

        holder.itemView.setOnClickListener {
            onItemClick(food)
        }
    }

    fun updateFoodList(newList: List<FoodItem>) {
        foodList = newList
        notifyDataSetChanged()
    }
}