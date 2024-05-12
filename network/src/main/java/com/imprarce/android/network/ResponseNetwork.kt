package com.imprarce.android.network

sealed class ResponseNetwork<out T> {
    data class Success<out T>(val result: T) : ResponseNetwork<T>()

    data class Failure(val exception: String) : ResponseNetwork<Nothing>()

    object Loading : ResponseNetwork<Nothing>()
}