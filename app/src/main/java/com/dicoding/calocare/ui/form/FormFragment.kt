package com.dicoding.calocare.ui.form

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.dicoding.calocare.R
import com.dicoding.calocare.data.Result
import com.dicoding.calocare.databinding.FragmentFormBinding
import com.dicoding.calocare.helper.NutritionHelper
import com.dicoding.calocare.ui.ViewModelFactory

class FormFragment : Fragment() {
    private lateinit var binding: FragmentFormBinding
    private lateinit var nutritionHelper: NutritionHelper

    private val formViewModel: FormViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }

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

//        // Retrieve the food name from the arguments (if any)
//        val foodName = arguments?.getString("FOOD_NAME") ?: ""
//        binding.foodNameInput.setText(foodName)

        binding.calculateButton.setOnClickListener {
            // Get user input
            val food_name = binding.foodNameInput.text.toString()
            val calories = binding.caloriesInput.text.toString().toDoubleOrNull() ?: 0.0
            val proteins = binding.proteinInput.text.toString().toDoubleOrNull() ?: 0.0
            val fat = binding.fatInput.text.toString().toDoubleOrNull() ?: 0.0
            val carbohydrate = binding.carbohydrateInput.text.toString().toDoubleOrNull() ?: 0.0

            // Evaluate nutrition
            val (total_nutrition, evaluation) = nutritionHelper.evaluateNutrition(
                calories,
                proteins,
                fat,
                carbohydrate
            )

//            // Prepare description (optional)
//            val descriptions = mapOf(
//                5 to "Bad for you",
//                4 to "Not very good for you",
//                3 to "Fairly good for you",
//                2 to "Good for you",
//                1 to "Very good for you"
//            )

            // Call addNewFood in FormViewModel
            formViewModel.addNewFood(
                food_name = food_name,
                carbohydrate = carbohydrate,
                protein = proteins,
                fat = fat,
                calories = calories,
                total_nutrition = total_nutrition,
                evaluation = evaluation.toString()
            )

            // Observe result
            formViewModel.addFoodResult.observe(viewLifecycleOwner) { result ->
                Result.Success {
                    // Handle success
                    Toast.makeText(requireContext(), "Food added successfully!", Toast.LENGTH_LONG)
                        .show()
                    findNavController().navigate(R.id.action_navigation_form_to_navigation_result)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        nutritionHelper.close() // Close the interpreter
    }
}
