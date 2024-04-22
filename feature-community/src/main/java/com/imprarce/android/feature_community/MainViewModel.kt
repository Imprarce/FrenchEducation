package com.imprarce.android.feature_community

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.imprarce.android.local.AppDatabase
import com.imprarce.android.local.ResponseRoom
import com.imprarce.android.local.user.room.UserDbEntity
import com.imprarce.android.local.user.room.UserRepository
import com.imprarce.android.network.repository.FirebaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: FirebaseRepository,
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _userFromRoom = MutableLiveData<UserDbEntity>()
    val userFromRoom: LiveData<UserDbEntity> = _userFromRoom

    val currentUser: FirebaseUser?
        get() = repository.currentUser

    init {
        getUser()
        Log.d("MainViewModel", "ViewModel created")
    }

    fun logOut() {
        repository.logOut()
    }

    fun getUser() = viewModelScope.launch {
        when (val response = repository.currentUser) {
            null -> {
            }
            else -> {
                val id_user = response.uid
                Log.d("MainViewModel", id_user)
                when (val userResponse = userRepository.getUserById(id_user)) {
                    is ResponseRoom.Success -> {
                        _userFromRoom.value = userResponse.result!!
                    }
                    is ResponseRoom.Failure -> {
                        Log.e("MainViewModel", "Failed to load user: ${userResponse.exception}")
                    }
                    is ResponseRoom.Loading -> {

                    }
                }
            }
        }
    }

    fun changeName(name: String) = viewModelScope.launch {
        repository.changeName(name)
        val currentUser = repository.currentUser
        if (currentUser != null) {
            userRepository.updateUserName(currentUser.uid, name)
        }
        getUser()
    }

    fun changePhoto(photoUri: Uri) = viewModelScope.launch {
        repository.changePhoto(photoUri)
        val photoUrl = repository.getPhotoUrl()
        val currentUser = repository.currentUser
        if (currentUser != null) {
            userRepository.updateUserPhoto(currentUser.uid, photoUrl)
        }
        getUser()
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("MainViewModel", "ViewModel destroyed")
    }
}