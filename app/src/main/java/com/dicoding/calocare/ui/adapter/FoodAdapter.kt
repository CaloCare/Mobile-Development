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
    private val onItemClick: (String) -> Unit
) : RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    inner class FoodViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemFoodBinding.bind(view)

        fun bind(food: FoodItem) {
            binding.textViewFoodName.text = food.foodName
            binding.textViewCalories.text = food.calories.toString()
            binding.textViewResult.text = food.evaluation

            itemView.setOnClickListener {
                onItemClick(food.id) //id atau name idk
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_food, parent, false)
        return FoodViewHolder(view)
    }

    override fun getItemCount(): Int = foodList.size

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.bind(foodList[position])
    }

    fun updateFoodList(newList: List<FoodItem>) {
        foodList = newList
        notifyDataSetChanged()
    }
}