package com.dicoding.calocare.data

sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val message: String, val data: Any? = null) : Result<Nothing>()
    object Loading : Result<Nothing>()
}