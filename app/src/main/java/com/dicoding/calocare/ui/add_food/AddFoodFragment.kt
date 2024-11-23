package com.dicoding.calocare.ui.add_food

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.dicoding.calocare.R
import com.dicoding.calocare.databinding.FragmentAddFoodBinding

class AddFoodFragment : Fragment() {

    private lateinit var binding: FragmentAddFoodBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddFoodBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imageButtonDishes.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_add_food_to_formFragment)
        }
    }
}
