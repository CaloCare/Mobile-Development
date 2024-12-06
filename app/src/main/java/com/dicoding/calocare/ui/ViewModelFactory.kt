package com.dicoding.calocare.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.calocare.data.repository.FoodRepository
import com.dicoding.calocare.di.Injection
import com.dicoding.calocare.ui.add_food.AddFoodViewModel
import com.dicoding.calocare.ui.chatbot.ChatbotViewModel
import com.dicoding.calocare.ui.form.FormViewModel
import com.dicoding.calocare.ui.home.HomeViewModel
import com.dicoding.calocare.ui.media_chooser.MediaChooserViewModel
import com.dicoding.calocare.ui.result.ResultViewModel
import com.dicoding.calocare.ui.settings.SettingsViewModel

class ViewModelFactory(private val foodRepository: FoodRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(foodRepository) as T
            }
            modelClass.isAssignableFrom(AddFoodViewModel::class.java) -> {
                AddFoodViewModel() as T
            }
            modelClass.isAssignableFrom(ChatbotViewModel::class.java) -> {
                ChatbotViewModel() as T
            }
            modelClass.isAssignableFrom(ResultViewModel::class.java) -> {
                ResultViewModel(foodRepository) as T
            }
            modelClass.isAssignableFrom(SettingsViewModel::class.java) -> {
                SettingsViewModel() as T
            }
            modelClass.isAssignableFrom(MediaChooserViewModel::class.java) -> {
                MediaChooserViewModel() as T
            }
            modelClass.isAssignableFrom(FormViewModel::class.java) -> {
                FormViewModel(foodRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory {
            return instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.foodRepository(context)).also { instance = it }
            }
        }
    }
}