package com.dicoding.calocare.ui.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dicoding.calocare.databinding.FragmentHomeBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class HomeFragment : Fragment() {

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