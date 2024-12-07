package com.dicoding.calocare.ui.result

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.dicoding.calocare.R
import com.dicoding.calocare.databinding.FragmentResultBinding
import com.dicoding.calocare.ui.ViewModelFactory
import com.dicoding.calocare.ui.form.FormViewModel


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

        resultViewModel.foodResult.observe(viewLifecycleOwner) { foodItem ->
            foodItem?.let {
                binding.textViewFoodName.text = it.foodName
                binding.textViewCarbohydrate.text = it.carbohydrate.toString()
                binding.textViewProtein.text = it.protein.toString()
                binding.textViewFat.text = it.fat.toString()
                binding.textViewCalories.text = it.calories.toString()
                binding.textViewTotalNutrition.text = it.totalNutrition.toString()

                // Get the evaluation score as an integer
                val evaluationScore = it.evaluation?.toIntOrNull() ?: 0

                // Get the corresponding description
                val evaluationDescription = descriptions[evaluationScore] ?: "No description available"

                // Set the evaluation text to include both the score and the description
                binding.textViewEvaluation.text = "$evaluationScore ($evaluationDescription)"
            }
        }

        binding.buttonDelete.setOnClickListener {
            val foodName = resultViewModel.foodResult.value?.foodName
            if (foodName != null) {
                resultViewModel.deleteFoodByName(foodName)
                resultViewModel.foodDeleted.observe(viewLifecycleOwner) { isDeleted ->
                    if (isDeleted) {
                        findNavController().navigate(R.id.action_navigation_result_to_navigation_home)
                    } else {
                        Toast.makeText(context, "Failed to delete food", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        binding.buttonAskChatbot.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_result_to_navigation_chatbot)
        }
    }
}