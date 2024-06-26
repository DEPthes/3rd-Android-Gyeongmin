package com.example.android_basic_study_08.utils

sealed class UiState<out T>{
    data object Loading: UiState<Nothing>()
    data class Success<out T>(val data: T): UiState<T>()
    data class Failure(val code: Int?, val message: String): UiState<Nothing>()
}