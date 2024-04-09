package com.imprarce.android.frencheducation.ui.greeting

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.imprarce.android.frencheducation.data.api.ResponseFirebase
import com.imprarce.android.frencheducation.data.api.repository.FirebaseRepository
import com.imprarce.android.frencheducation.data.db.user.room.UserDbEntity
import com.imprarce.android.frencheducation.data.db.user.room.UserRepository
import com.imprarce.android.frencheducation.utils.DateFormatUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class FirebaseViewModel @Inject constructor(
    private val repository: FirebaseRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _loginLiveData = MutableLiveData<ResponseFirebase<FirebaseUser>?>(null)
    val loginLiveData: LiveData<ResponseFirebase<FirebaseUser>?> = _loginLiveData

    private val _signUpLiveData = MutableLiveData<ResponseFirebase<FirebaseUser>?>(null)
    val signUpLiveData: LiveData<ResponseFirebase<FirebaseUser>?> = _signUpLiveData

    private val _userFromRoom = MutableLiveData<UserDbEntity>(null)
    val userFromRoom: LiveData<UserDbEntity> = _userFromRoom

    val currentUser: FirebaseUser?
    get() = repository.currentUser

    init {
        if(repository.currentUser != null){
            _loginLiveData.value = ResponseFirebase.Success(repository.currentUser!!)
        }
        Log.d("MyViewModel", "ViewModel created")
    }

    fun login(email: String, password: String) = viewModelScope.launch {
        _loginLiveData.value = ResponseFirebase.Loading
        val result = repository.login(email, password)
        _loginLiveData.value = result
        addCurrentUserToRoomIfNotExists()
    }

    fun signUp(email: String, password: String) = viewModelScope.launch {
        _signUpLiveData.value = ResponseFirebase.Loading
        val result = repository.signUp(email, password)
        _signUpLiveData.value = result
        addCurrentUserToRoomIfNotExists()
    }

    fun logOut(){
        repository.logOut()
        _loginLiveData.value = null
        _signUpLiveData.value = null
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

    private fun addCurrentUserToRoomIfNotExists() = viewModelScope.launch {
        val currentUser = repository.currentUser
        if (currentUser != null) {
            val id_user = currentUser.uid
            _userFromRoom.value = userRepository.getUserById(id_user)
            if (_userFromRoom.value == null) {
                val data = DateFormatUtil.formatDate(currentUser.metadata?.creationTimestamp?.let { Date(it) })
                userRepository.insertUser(UserDbEntity(id_user, "*****", currentUser.email ?: "", currentUser.displayName ?: currentUser.email, null, data))
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("MyViewModel", "ViewModel destroyed")
    }

}