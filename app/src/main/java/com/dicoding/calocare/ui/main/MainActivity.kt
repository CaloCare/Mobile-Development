package com.dicoding.calocare.ui.main

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.dicoding.calocare.R
import com.dicoding.calocare.data.pref.PreferencesHelper
import com.dicoding.calocare.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        val preferencesHelper = PreferencesHelper(this)
        if (preferencesHelper.isDarkMode()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_add_food,
                R.id.navigation_chatbot,
                R.id.navigation_settings
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // Set up the navigation item selected listener
        navView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_home,
                R.id.navigation_add_food,
                R.id.navigation_chatbot,
                R.id.navigation_settings -> {
                    navigateToFragment(menuItem.itemId, navController)
                    true
                }
                else -> false
            }
        }
    }
    private fun navigateToFragment(fragmentId: Int, navController: NavController) {
        // Check if the current destination is the same as the new destination
        if (navController.currentDestination?.id != fragmentId) {
            // Clear the back stack to the start destination
            navController.popBackStack(navController.graph.startDestinationId, false)
            // Navigate to the new fragment
            navController.navigate(fragmentId)
        }
    }
}