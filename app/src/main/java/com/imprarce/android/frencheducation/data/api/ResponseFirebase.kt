package com.imprarce.android.frencheducation.data.api

sealed class ResponseFirebase<out T> {
    data class Success<out T>(val result: T): ResponseFirebase<T>()
    data class Failure(val exception: Exception): ResponseFirebase<Nothing>()
    object Loading: ResponseFirebase<Nothing>()
}