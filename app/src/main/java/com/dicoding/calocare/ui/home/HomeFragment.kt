package com.dicoding.calocare.ui.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.calocare.R
import com.dicoding.calocare.databinding.FragmentHomeBinding
import com.dicoding.calocare.ui.ViewModelFactory
import com.dicoding.calocare.ui.adapter.FoodAdapter
import com.dicoding.calocare.ui.result.ResultViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }
    private val resultViewModel: ResultViewModel by activityViewModels {
        ViewModelFactory.getInstance(requireContext())
    }
    private lateinit var foodAdapter: FoodAdapter
    private lateinit var binding: FragmentHomeBinding
    private val handler = Handler(Looper.getMainLooper())
    private val calendar = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        setupDate()
        setupButtonListeners()
        scheduleDateUpdate()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeFoodItems()
        viewModel.getAllFood()

        viewModel.foods.observe(viewLifecycleOwner) { foods ->
            foodAdapter.updateFoodList(foods)
        }
        viewModel.fetchAllFoods()
    }

    private fun setupRecyclerView() {
        foodAdapter = FoodAdapter(emptyList()) { foodItem ->
            Log.d("HomeFragment", "Clicked on food: $foodItem")
            Log.d("HomeFragment", """
            Clicked Food Item:
            Name: ${foodItem.foodName}
            Carbohydrate: ${foodItem.carbohydrate}
            Protein: ${foodItem.protein}
            Fat: ${foodItem.fat}
            Calories: ${foodItem.calories}
        """.trimIndent())

            resultViewModel.setFoodResult(foodItem)
            findNavController().navigate(R.id.action_navigation_home_to_navigation_result)
        }

        binding.dishesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.dishesRecyclerView.adapter = foodAdapter

        Log.d("HomeFragment", "RecyclerView setup complete")
    }

    private fun observeFoodItems() {
        viewModel.foodItem.observe(viewLifecycleOwner) { foodItems ->
            if (foodItems != null) {
                Log.d("HomeFragment", "Observed food items: ${foodItems.size}")
                foodAdapter.updateFoodList(foodItems)
                foodAdapter.notifyDataSetChanged()
            } else {
                Log.d("HomeFragment", "No food items received")
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllFood()
    }

    private fun setupDate() {
        updateDate()
    }

    private fun updateDate() {
        val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH)
        val formattedDate = dateFormat.format(calendar.time)
        binding.tvDate.text = formattedDate
    }

    private fun setupButtonListeners() {
        binding.btnDateLeft.setOnClickListener {
            calendar.add(Calendar.DAY_OF_YEAR, -1)
            updateDate()
        }

        binding.btnDateRight.setOnClickListener {
            calendar.add(Calendar.DAY_OF_YEAR, 1)
            updateDate()
        }
    }

    private fun scheduleDateUpdate() {
        val now = Calendar.getInstance()
        val midnight = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
            add(Calendar.DAY_OF_YEAR, 1)
        }
        val delay = midnight.timeInMillis - now.timeInMillis

        handler.postDelayed({
            calendar.timeInMillis = midnight.timeInMillis
            updateDate()
            scheduleDateUpdate()
        }, delay)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacksAndMessages(null)
    }
}