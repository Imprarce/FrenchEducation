package com.imprarce.android.frencheducation.data.api.repository

import com.google.firebase.auth.FirebaseUser
import com.imprarce.android.frencheducation.data.api.ResponseFirebase

interface FirebaseRepository {
    val currentUser: FirebaseUser?
    suspend fun login(email: String, password: String): ResponseFirebase<FirebaseUser>
    suspend fun signUp(name: String, email: String, password: String): ResponseFirebase<FirebaseUser>
    fun logOut()
}