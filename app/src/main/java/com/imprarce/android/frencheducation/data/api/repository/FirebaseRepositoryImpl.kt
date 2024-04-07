package com.imprarce.android.frencheducation.data.api.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.imprarce.android.frencheducation.data.api.ResponseFirebase
import com.imprarce.android.frencheducation.data.api.utils.await
import javax.inject.Inject

class FirebaseRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : FirebaseRepository {
    override val currentUser: FirebaseUser?
        get() = firebaseAuth.currentUser

    override suspend fun login(email: String, password: String): ResponseFirebase<FirebaseUser> {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            ResponseFirebase.Success(result.user!!)
        } catch (e: Exception){
            e.printStackTrace()
            ResponseFirebase.Failure(e)
        }
    }

    override suspend fun signUp(
        name: String,
        email: String,
        password: String
    ): ResponseFirebase<FirebaseUser> {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            result?.user?.updateProfile(UserProfileChangeRequest.Builder().setDisplayName(name).build())?.await()
            ResponseFirebase.Success(result.user!!)
        } catch (e: Exception){
            e.printStackTrace()
            ResponseFirebase.Failure(e)
        }
    }

    override fun logOut() {
        firebaseAuth.signOut()
    }
}