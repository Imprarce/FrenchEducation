package com.imprarce.android.frencheducation.data.api.repository

import android.net.Uri
import com.google.firebase.auth.FirebaseUser
import com.imprarce.android.frencheducation.data.api.ResponseFirebase

interface FirebaseRepository {
    val currentUser: FirebaseUser?
    suspend fun login(email: String, password: String): ResponseFirebase<FirebaseUser>
    suspend fun signUp(email: String, password: String): ResponseFirebase<FirebaseUser>

    suspend fun changeName(name: String)

    suspend fun getName() : String

    suspend fun changePhoto(photoUri: Uri)

    suspend fun getPhotoUrl(): String
    fun logOut()
}