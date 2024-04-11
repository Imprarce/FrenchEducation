package com.imprarce.android.frencheducation.ui

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.imprarce.android.frencheducation.data.api.repository.FirebaseRepository
import com.imprarce.android.frencheducation.data.db.AppDatabase
import com.imprarce.android.frencheducation.data.db.ResponseRoom
import com.imprarce.android.frencheducation.data.db.user.room.UserDbEntity
import com.imprarce.android.frencheducation.data.db.user.room.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: FirebaseRepository,
    private val userRepository: UserRepository,
    private val roomDatabase: AppDatabase
) : ViewModel() {

    private val job = Job()

    private val _userFromRoom = MutableLiveData<UserDbEntity>()
    val userFromRoom: LiveData<UserDbEntity> = _userFromRoom

    val currentUser: FirebaseUser?
        get() = repository.currentUser

    init {
        getUser()
        Log.d("MainViewModel", "ViewModel created")
    }

    fun logOut(){
        repository.logOut()
    }

    fun getUser() = viewModelScope.launch{
        when (val response = repository.currentUser) {
            null -> {
                // Обработка ситуации, когда пользователь не аутентифицирован
            }
            else -> {
                val id_user = response.uid
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

    private fun cancelAllCoroutines() {
        job.cancel()
    }
    override fun onCleared() {
        super.onCleared()
        roomDatabase.close()
        cancelAllCoroutines()
        Log.d("MainViewModel", "ViewModel destroyed")
    }
}