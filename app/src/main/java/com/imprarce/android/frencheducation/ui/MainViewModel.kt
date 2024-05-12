package com.imprarce.android.frencheducation.ui

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imprarce.android.local.ResponseRoom
import com.imprarce.android.local.user.room.UserDbEntity
import com.imprarce.android.local.user.room.UserRepository
import com.imprarce.android.network.ResponseNetwork
import com.imprarce.android.network.repository.user.UserRepositoryNetwork
import com.imprarce.android.network.utils.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: UserRepositoryNetwork,
    private val userRepository: UserRepository,
    private val sessionManager: SessionManager
) : ViewModel() {

    private var _userFromRoom = MutableLiveData<UserDbEntity?>()
    val userFromRoom: LiveData<UserDbEntity?> = _userFromRoom

    private val _userPhotoUrl = MutableLiveData<String>()
    val userPhotoUrl: LiveData<String> = _userPhotoUrl

    init {
        getUser()
        Log.d("MainViewModel", "ViewModel created")
    }

    suspend fun logout() {
        repository.logOut()
    }

    fun getUser() = viewModelScope.launch {
        val email = sessionManager.getCurrentUserEmail() ?: return@launch

        when (val response = userRepository.getUserByEmail(email)) {
            is ResponseRoom.Success -> {
                if (response.result != null) {
                    _userFromRoom.value = response.result
                    sessionManager.setCurrentUserId(response.result!!.id_user)
                }
            }
            else -> {}
        }
    }


    fun changeName(name: String) = viewModelScope.launch {
        try {
            repository.changeName(name)
            val userFromNetwork = repository.getUser()

            if(userFromRoom != null && userFromNetwork is ResponseNetwork.Success){
                val userFromNetworkData = userFromNetwork.result
                val id = sessionManager.getCurrentUserId()
                if (userFromNetworkData != null && id != null) {
                    if (userFromRoom.value?.userName != userFromNetworkData.userName) {
                        userRepository.updateUserName(id, userFromNetworkData.userName)
                        getUser()
                    }
                }
            }
        } catch (e: Exception){
            e.printStackTrace()
        }
    }

    fun changePhoto(photoUri: Uri) = viewModelScope.launch {
        try {
            repository.changePhoto(photoUri)

            val userFromNetwork = repository.getUser()

            if (userFromRoom != null && userFromNetwork is ResponseNetwork.Success) {
                val userFromNetworkData = userFromNetwork.result
                if (userFromNetworkData != null) {
                    if (userFromRoom.value?.imageUrl != userFromNetworkData.imageUrl) {
                        val updatedImageUrl = userFromNetworkData.imageUrl.replace("http://", "https://")
                        if (userFromRoom.value?.imageUrl != updatedImageUrl) {
                            userRepository.updateUserPhoto(userFromNetworkData.idUser, updatedImageUrl)
                            getUser()
                        }
                        _userPhotoUrl.value = updatedImageUrl
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    override fun onCleared() {
        super.onCleared()
        Log.d("MainViewModel", "ViewModel destroyed")
    }
}