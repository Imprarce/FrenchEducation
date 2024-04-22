package com.imprarce.android.feature_login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.imprarce.android.feature_login.utils.DateFormatUtil
import com.imprarce.android.local.ResponseRoom
import com.imprarce.android.local.user.room.UserDbEntity
import com.imprarce.android.local.user.room.UserRepository
import com.imprarce.android.network.ResponseFirebase
import com.imprarce.android.network.repository.FirebaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class GreetingViewModel @Inject constructor(
    private val firebaseRepository: FirebaseRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _loginLiveData = MutableLiveData<ResponseFirebase<FirebaseUser>?>(null)
    val loginLiveData: LiveData<ResponseFirebase<FirebaseUser>?> = _loginLiveData

    private val _signUpLiveData = MutableLiveData<ResponseFirebase<FirebaseUser>?>(null)
    val signUpLiveData: LiveData<ResponseFirebase<FirebaseUser>?> = _signUpLiveData

    val currentUser: FirebaseUser?
        get() = firebaseRepository.currentUser

    init {
        if(firebaseRepository.currentUser != null){
            _loginLiveData.value = ResponseFirebase.Success(firebaseRepository.currentUser!!)
            onCleared()
        }
        Log.d("GreetingViewModel", "ViewModel created")
    }

    fun login(email: String, password: String) = viewModelScope.launch {
        _loginLiveData.value = ResponseFirebase.Loading
        val result = firebaseRepository.login(email, password)
        _loginLiveData.value = result
        addCurrentUserToRoomIfNotExists()
    }

    fun signUp(email: String, password: String) = viewModelScope.launch {
        _signUpLiveData.value = ResponseFirebase.Loading
        val result = firebaseRepository.signUp(email, password)
        _signUpLiveData.value = result
        addCurrentUserToRoomIfNotExists()
    }

    private fun addCurrentUserToRoomIfNotExists() = viewModelScope.launch {
        val currentUser = firebaseRepository.currentUser
        if (currentUser != null) {
            val id_user = currentUser.uid
            when(userRepository.getUserById(id_user)){
                is ResponseRoom.Failure -> {
                    val name = firebaseRepository.getName()
                    val imageUrl = firebaseRepository.getPhotoUrl()
                    val data = DateFormatUtil.formatDate(currentUser.metadata?.creationTimestamp?.let { Date(it) })
                    userRepository.insertUser(UserDbEntity(id_user, "*****", currentUser.email ?: "", name, imageUrl, data))
                }
                else -> {
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("GreetingViewModel", "ViewModel destroyed")
    }
}