package com.dicoding.calocare.ui.media_chooser

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MediaChooserViewModel : ViewModel() {

    private val _navigationEvent = MutableLiveData<Event<NavigationAction>>()
    val navigationEvent: LiveData<Event<NavigationAction>> = _navigationEvent

    fun onGalleryClicked() {
        _navigationEvent.value = Event(NavigationAction.OpenGallery)
    }

    fun onCameraClicked() {
        _navigationEvent.value = Event(NavigationAction.OpenCamera)
    }
}

// Define your navigation actions
sealed class NavigationAction {
    object OpenGallery : NavigationAction()
    object OpenCamera : NavigationAction()
}

// Event wrapper to handle one-time events
class Event<out T>(private val content: T) {
    private var hasBeenHandled = false

    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }
}