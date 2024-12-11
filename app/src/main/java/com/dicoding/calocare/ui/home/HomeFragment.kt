package com.dicoding.calocare.ui.home

import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.calocare.databinding.FragmentHomeBinding
import com.dicoding.calocare.ui.ViewModelFactory
import com.dicoding.calocare.ui.adapter.FoodAdapter
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels {
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
        setupRecyclerView()
        observeFoodList()
        return binding.root
    }

    private fun observeFoodList() {
        viewModel.foodList.observe(viewLifecycleOwner) { foodItems ->
            Log.d("HomeFragment", "Observed food items: $foodItems")
            foodAdapter.updateFoodList(foodItems)
        }
        viewModel.getAllFood() // Fetch the food list
    }

    private fun setupRecyclerView() {
        foodAdapter = FoodAdapter(emptyList()) { foodId ->
            // Handle item click, e.g., navigate to detail view
        }
        binding.dishesRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = foodAdapter
        }
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