package com.dicoding.calocare.ui.form

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.dicoding.calocare.R
import com.dicoding.calocare.databinding.FragmentChatbotBinding
import com.dicoding.calocare.databinding.FragmentFormBinding
import com.dicoding.calocare.helper.NutritionHelper


class FormFragment : Fragment() {
    private lateinit var binding: FragmentFormBinding
    private lateinit var nutritionHelper: NutritionHelper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFormBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the NutritionHelper with context and callbacks
        nutritionHelper = NutritionHelper(
            context = requireContext(),
            onResult = { result ->
                // Handle successful evaluation result
                Toast.makeText(requireContext(), result, Toast.LENGTH_LONG).show()
            },
            onError = { error ->
                // Handle error
                Toast.makeText(requireContext(), "Error: $error", Toast.LENGTH_LONG).show()
            }
        )

        // Retrieve the food name from the arguments
        val foodName = arguments?.getString("FOOD_NAME") ?: ""
        binding.foodNameInput.setText(foodName)

        binding.calculateButton.setOnClickListener {
            // Get user input
            val calories = binding.caloriesInput.text.toString().toDoubleOrNull() ?: 0.0
            val proteins = binding.proteinInput.text.toString().toDoubleOrNull() ?: 0.0
            val fat = binding.fatInput.text.toString().toDoubleOrNull() ?: 0.0
            val carbohydrate = binding.carbohydrateInput.text.toString().toDoubleOrNull() ?: 0.0

            // Evaluate nutrition
            val (totalNutrition, evaluation) = nutritionHelper.evaluateNutrition(
                calories,
                proteins,
                fat,
                carbohydrate
            )

            // Prepare description
            val descriptions = mapOf(
                5 to "buruk untuk Anda",
                4 to "tidak terlalu baik untuk Anda",
                3 to "cukup baik untuk Anda",
                2 to "baik untuk Anda",
                1 to "sangat baik untuk Anda"
            )

            // Create a Bundle to pass data to ResultFragment
            val bundle = Bundle().apply {
                putString("TOTAL_NUTRITION", totalNutrition.toString())
                putString("EVALUATION", "${evaluation} (${descriptions[evaluation]})")
                putString("FOOD_NAME", foodName) // Pass food name if needed
            }

            // Navigate to ResultFragment with the Bundle
            findNavController().navigate(R.id.action_navigation_form_to_navigation_result, bundle)
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        nutritionHelper.close() // Close the interpreter when the fragment is destroyed
    }

}


