package com.dicoding.calocare.ui.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import com.dicoding.calocare.data.pref.PreferencesHelper
import com.dicoding.calocare.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding
    private lateinit var preferencesHelper: PreferencesHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        preferencesHelper = PreferencesHelper(requireContext())

        binding.switchTheme.isChecked = preferencesHelper.isDarkMode()

        binding.switchTheme.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                preferencesHelper.setDarkMode(true)
                requireActivity().recreate()
            } else {
                preferencesHelper.setDarkMode(false)
                requireActivity().recreate()
            }
        }
        return binding.root
    }
}