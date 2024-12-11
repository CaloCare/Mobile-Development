package com.dicoding.calocare.ui.result

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.dicoding.calocare.R
import com.dicoding.calocare.databinding.FragmentResultBinding
import com.dicoding.calocare.ui.ViewModelFactory
import com.dicoding.calocare.data.Result


class ResultFragment : Fragment() {

    private lateinit var binding: FragmentResultBinding

    private val resultViewModel: ResultViewModel by activityViewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    // Map for evaluation descriptions
    private val descriptions = mapOf(
        5 to "Bad for you",
        4 to "Not very good for you",
        3 to "Fairly good for you",
        2 to "Good for you",
        1 to "Very good for you"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("ResultFragment", "Fragment created")
        Log.d("ResultFragment", "Initial food result: ${resultViewModel.foodResult.value}")

        resultViewModel.foodResult.observe(viewLifecycleOwner) { foodItem ->
            Log.d("ResultFragment", "Observing food item: $foodItem")

            foodItem?.let {
                Log.d("ResultFragment", """
                    Food Details:
                    Name: ${it.foodName}
                    Carbohydrate: ${it.carbohydrate}
                    Protein: ${it.protein}
                    Fat: ${it.fat}
                    Calories: ${it.calories}
                    Total Nutrition: ${it.totalNutrition}
                    Evaluation: ${it.evaluation}
                """.trimIndent())

                binding.textViewFoodName.text = it.foodName ?: "N/A"
                binding.textViewCarbohydrate.text = String.format("%.1f", it.carbohydrate)
                binding.textViewProtein.text = String.format("%.1f", it.protein)
                binding.textViewFat.text = String.format("%.1f", it.fat)
                binding.textViewCalories.text = it.calories.toString()
                binding.textViewTotalNutrition.text = String.format("%.1f", it.totalNutrition)

                val evaluationScore = it.evaluation?.toIntOrNull() ?: 0
                val evaluationDescription = descriptions[evaluationScore] ?: "No description available"
                binding.textViewEvaluation.text = "$evaluationScore ($evaluationDescription)"
            } ?: run {
                Log.e("ResultFragment", "Food item is null")
            }
        }

        binding.buttonDelete.setOnClickListener {
            val foodName = resultViewModel.foodResult.value?.foodName
            if (!foodName.isNullOrBlank()) {
                showDeleteConfirmationDialog(foodName)
            } else {
                Toast.makeText(context, "Nama makanan tidak valid", Toast.LENGTH_SHORT).show()
            }
        }

        resultViewModel.foodDeleted.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.buttonDelete.isEnabled = false
                }
                is Result.Success -> {
                    findNavController().navigate(R.id.action_navigation_result_to_navigation_home)
                    Toast.makeText(context, "Makanan berhasil dihapus", Toast.LENGTH_SHORT).show()
                }
                is Result.Error -> {
                    binding.buttonDelete.isEnabled = true

                    val errorMessage = when {
                        result.message.contains("kosong", ignoreCase = true) -> "Nama makanan tidak valid"
                        result.message.contains("HTTP", ignoreCase = true) -> "Gagal terhubung ke server"
                        else -> "Gagal menghapus makanan: ${result.message}"
                    }

                    Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.buttonAskChatbot.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_result_to_navigation_chatbot)
        }
    }

    private fun showDeleteConfirmationDialog(foodName: String) {
        AlertDialog.Builder(requireContext())
            .setTitle("Delete Food")
            .setMessage("Are you sure you want to delete $foodName?")
            .setPositiveButton("Delete") { _, _ ->
                resultViewModel.deleteFoodByName(foodName)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    override fun onStart() {
        super.onStart()
        Log.d("ResultFragment", "onStart called")
    }

    override fun onResume() {
        super.onResume()
        Log.d("ResultFragment", "onResume called")
    }
}