package com.imprarce.android.frencheducation.ui

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.imprarce.android.frencheducation.data.api.repository.FirebaseRepository
import com.imprarce.android.frencheducation.data.db.AppDatabase
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


    private val _userFromRoom = MutableLiveData<UserDbEntity>(null)
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
        val currentUser = repository.currentUser
        if (currentUser != null) {
            val id_user = currentUser.uid
            _userFromRoom.value = userRepository.getUserById(id_user)
        }
    }

    fun changeName(name: String) = viewModelScope.launch {
        repository.changeName(name)
        userFromRoom.value?.let { userRepository.updateUserName(it.id_user, name) }
        getUser()
    }

    fun changePhoto(photoUri: Uri) = viewModelScope.launch {
        repository.changePhoto(photoUri)
        val photoUrl = repository.getPhotoUrl()
        userFromRoom.value?.let { userRepository.updateUserPhoto(it.id_user, photoUrl) }
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