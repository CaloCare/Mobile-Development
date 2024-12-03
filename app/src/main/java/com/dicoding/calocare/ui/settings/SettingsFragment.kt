package com.dicoding.calocare.ui.settings

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.dicoding.calocare.data.pref.PreferencesHelper
import com.dicoding.calocare.databinding.FragmentSettingsBinding
import java.util.Calendar

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
            preferencesHelper.setDarkMode(isChecked)
            requireActivity().recreate()
        }

        binding.switchNotification.isChecked = preferencesHelper.isNotificationEnabled()
        binding.switchNotification.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    requestNotificationPermission()
                } else {
                    preferencesHelper.setNotificationEnabled(true)
                    setupDailyNotification(true)
                }
            } else {
                preferencesHelper.setNotificationEnabled(false)
                setupDailyNotification(false)
            }
        }
        return binding.root
    }

    private fun setupDailyNotification(isEnabled: Boolean) {
        val workManager = WorkManager.getInstance(requireContext())

        if (isEnabled) {
            val dailyWorkRequest = PeriodicWorkRequestBuilder<DailyNotificationWorker>(1, java.util.concurrent.TimeUnit.DAYS)
                .setInitialDelay(calculateInitialDelay(), java.util.concurrent.TimeUnit.MILLISECONDS)
                .build()

            workManager.enqueueUniquePeriodicWork(
                "daily_notification_work",
                ExistingPeriodicWorkPolicy.REPLACE,
                dailyWorkRequest
            )
        } else {
            workManager.cancelUniqueWork("daily_notification_work")
        }
    }

    private fun calculateInitialDelay(): Long {
        val now = Calendar.getInstance()
        val nextMidnight = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
            add(Calendar.DAY_OF_YEAR, 1)
        }
        return nextMidnight.timeInMillis - now.timeInMillis
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (requireContext().checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) !=
                android.content.pm.PackageManager.PERMISSION_GRANTED) {
                requestPermissions(
                    arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                    NOTIFICATION_PERMISSION_REQUEST_CODE
                )
            } else {
                preferencesHelper.setNotificationEnabled(true)
                setupDailyNotification(true)
            }
        } else {
            preferencesHelper.setNotificationEnabled(true)
            setupDailyNotification(true)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == NOTIFICATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == android.content.pm.PackageManager.PERMISSION_GRANTED) {
                preferencesHelper.setNotificationEnabled(true)
                setupDailyNotification(true)
            } else {
                binding.switchNotification.isChecked = false
            }
        }
    }

    private companion object {
        const val NOTIFICATION_PERMISSION_REQUEST_CODE = 100
    }
}