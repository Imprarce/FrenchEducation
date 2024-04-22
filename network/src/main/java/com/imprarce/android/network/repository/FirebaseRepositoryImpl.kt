package com.imprarce.android.network.repository

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageReference
import com.imprarce.android.network.ResponseFirebase
import com.imprarce.android.network.utils.await
import javax.inject.Inject

class FirebaseRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseReference: DatabaseReference,
    private val firebaseStorage: StorageReference
) : FirebaseRepository {
    override val currentUser: FirebaseUser?
        get() = firebaseAuth.currentUser

    override suspend fun login(email: String, password: String): ResponseFirebase<FirebaseUser> {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            ResponseFirebase.Success(result.user!!)
        } catch (e: Exception) {
            e.printStackTrace()
            ResponseFirebase.Failure(e)
        }
    }

    override suspend fun signUp(
        email: String,
        password: String
    ): ResponseFirebase<FirebaseUser> {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            result?.user?.updateProfile(
                UserProfileChangeRequest.Builder().setDisplayName(email).build()
            )?.await()
            val user_id = result.user?.uid
            if (user_id != null) {
                firebaseReference.child(user_id).child("name").setValue(email)
            }
            ResponseFirebase.Success(result.user!!)
        } catch (e: Exception) {
            e.printStackTrace()
            ResponseFirebase.Failure(e)
        }
    }

    override suspend fun changeName(name: String) {
        if (currentUser != null) {
            firebaseReference.child(currentUser!!.uid).child("name").setValue(name)
        }
    }

    override suspend fun getName(): String {
        var name = ""
        try {
            if (currentUser != null) {
                val snapshot =
                    firebaseReference.child(currentUser!!.uid).child("name").get().await()
                name = snapshot.getValue(String::class.java) ?: ""
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        if (name == "") name = currentUser?.email.toString()
        return name
    }

    override suspend fun changePhoto(photoUri: Uri) {
        try {
            val storageRef = firebaseStorage.child("images/${currentUser!!.uid}")
            val uploadTask = storageRef.putFile(photoUri)
            uploadTask.await()
            val downloadUrl = storageRef.downloadUrl.await()

            val profileUpdates = UserProfileChangeRequest.Builder()
                .setPhotoUri(downloadUrl)
                .build()

            currentUser!!.updateProfile(profileUpdates).await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun getPhotoUrl(): String {

        var photoUrl: String = ""
        try {
            val storageRef = firebaseStorage.child("images/${currentUser!!.uid}")
            photoUrl = storageRef.downloadUrl.await().toString()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return photoUrl
    }

    override fun logOut() {
        firebaseAuth.signOut()
    }
}