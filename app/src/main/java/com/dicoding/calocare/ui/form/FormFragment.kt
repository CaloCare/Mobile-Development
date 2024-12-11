package com.dicoding.calocare.ui.form

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.dicoding.calocare.R
import com.dicoding.calocare.data.Result
import com.dicoding.calocare.data.remote.response.FoodItem
import com.dicoding.calocare.databinding.FragmentFormBinding
import com.dicoding.calocare.helper.NutritionHelper
import com.dicoding.calocare.ui.ViewModelFactory
import com.dicoding.calocare.ui.result.ResultViewModel

class FormFragment : Fragment() {
    private lateinit var binding: FragmentFormBinding
    private lateinit var nutritionHelper: NutritionHelper

    private val formViewModel: FormViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    private val resultViewModel: ResultViewModel by activityViewModels {
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

        nutritionHelper = NutritionHelper(
            context = requireContext(),
            onResult = { result ->
                Toast.makeText(requireContext(), result, Toast.LENGTH_LONG).show()
            },
            onError = { error ->
                Toast.makeText(requireContext(), "Error: $error", Toast.LENGTH_LONG).show()
            }
        )

//        // Retrieve the food name from the arguments (if any)
//        val foodName = arguments?.getString("FOOD_NAME") ?: ""
//        binding.foodNameInput.setText(foodName)

        // Observe result
        formViewModel.addFoodResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Success -> {
                    //                    hideLoading()
                    Toast.makeText(requireContext(), "Food added successfully!", Toast.LENGTH_LONG)
                        .show()
//                    findNavController().navigate(R.id.action_navigation_form_to_navigation_result) kalau ada navigation ini bakal error, salah satu aja di 1 kode ini aja yg muncul
                }

                is Result.Error -> {
//                    hideLoading()
                    Toast.makeText(
                        requireContext(),
                        "Error: ${result.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }

                is Result.Loading -> {
//                    showLoading()
                }
            }
        }

        binding.calculateButton.setOnClickListener {
            val foodName = binding.foodNameInput.text.toString()
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

//            // Prepare description (optional)
//            val descriptions = mapOf(
//                5 to "Bad for you",
//                4 to "Not very good for you",
//                3 to "Fairly good for you",
//                2 to "Good for you",
//                1 to "Very good for you"
//            )

            formViewModel.addNewFood(
                foodName,
                carbohydrate,
                proteins,
                fat,
                calories.toInt(),
                totalNutrition,
                evaluation.toString()
            ) { foodItem ->
                resultViewModel.setFoodResult(foodItem)
                findNavController().navigate(
                    R.id.action_navigation_form_to_navigation_result
                )
            }
        }
    }

//    private fun showLoading() {
//        binding.progressBar.visibility = View.VISIBLE
//    }
//
//    private fun hideLoading() {
//        binding.progressBar.visibility = View.GONE
//    }

    override fun onDestroy() {
        super.onDestroy()
        nutritionHelper.close() // Close the interpreter
    }
}
