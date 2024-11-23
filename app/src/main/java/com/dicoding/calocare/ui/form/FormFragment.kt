package com.dicoding.calocare.ui.form

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dicoding.calocare.R
import com.dicoding.calocare.databinding.FragmentChatbotBinding
import com.dicoding.calocare.databinding.FragmentFormBinding


class FormFragment : Fragment() {
    private lateinit var binding: FragmentFormBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFormBinding.inflate(inflater, container, false)
        return binding.root
    }
}