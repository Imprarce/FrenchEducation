package com.imprarce.android.frencheducation.ui.greeting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.imprarce.android.frencheducation.data.api.ResponseFirebase
import com.imprarce.android.frencheducation.data.api.repository.FirebaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GreetingViewModel @Inject constructor(
    private val repository: FirebaseRepository
) : ViewModel() {

    private val _loginLiveData = MutableLiveData<ResponseFirebase<FirebaseUser>?>(null)
    val loginLiveData: LiveData<ResponseFirebase<FirebaseUser>?> = _loginLiveData

    private val _signUpLiveData = MutableLiveData<ResponseFirebase<FirebaseUser>?>(null)
    val signUpLiveData: LiveData<ResponseFirebase<FirebaseUser>?> = _signUpLiveData

    val currentUser: FirebaseUser?
    get() = repository.currentUser

    init {
        if(repository.currentUser != null){
            _loginLiveData.value = ResponseFirebase.Success(repository.currentUser!!)
        }
    }

    fun login(email: String, password: String) = viewModelScope.launch {
        _loginLiveData.value = ResponseFirebase.Loading
        val result = repository.login(email, password)
        _loginLiveData.value = result
    }

    fun signUp(name: String, email: String, password: String) = viewModelScope.launch {
        _signUpLiveData.value = ResponseFirebase.Loading
        val result = repository.signUp(name, email, password)
        _signUpLiveData.value = result
    }

    fun logOut(){
        repository.logOut()
        _loginLiveData.value = null
        _signUpLiveData.value = null
    }
}