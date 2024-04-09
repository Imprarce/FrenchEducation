package com.imprarce.android.frencheducation.di

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.imprarce.android.frencheducation.data.api.repository.FirebaseRepository
import com.imprarce.android.frencheducation.data.api.repository.FirebaseRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class FirebaseModule {

    @Provides
    fun provideFirebaseAuth() : FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    fun provideAuthRepository(firebaseRepositoryImpl: FirebaseRepositoryImpl): FirebaseRepository = firebaseRepositoryImpl

    @Provides
    fun provideFirebaseReference() : DatabaseReference = Firebase.database.reference.child("user_data")

    @Provides
    fun provideFirebaseStorage() : StorageReference = FirebaseStorage.getInstance().reference
}