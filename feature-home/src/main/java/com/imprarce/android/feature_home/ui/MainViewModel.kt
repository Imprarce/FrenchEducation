package com.imprarce.android.feature_home.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imprarce.android.local.ResponseRoom
import com.imprarce.android.local.user.room.UserDbEntity
import com.imprarce.android.local.user.room.UserRepository
import com.imprarce.android.network.utils.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: com.imprarce.android.network.repository.user.UserRepositoryNetwork,
    private val userRepository: UserRepository,
    private val sessionManager: SessionManager
) : ViewModel() {

    private var _userFromRoom = MutableLiveData<UserDbEntity?>()
    val userFromRoom: LiveData<UserDbEntity?> = _userFromRoom

    init {
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

    override fun onCleared() {
        super.onCleared()
        Log.d("MainViewModel", "ViewModel destroyed")
    }
}