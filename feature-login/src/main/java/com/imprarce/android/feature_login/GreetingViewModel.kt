package com.imprarce.android.feature_login

import android.util.Log
import androidx.lifecycle.*
import com.imprarce.android.feature_login.helpers.ConverterUser
import com.imprarce.android.local.ResponseRoom
import com.imprarce.android.local.user.room.UserDbEntity
import com.imprarce.android.local.user.room.UserRepository
import com.imprarce.android.network.ResponseNetwork
import com.imprarce.android.network.model.user.User
import com.imprarce.android.network.model.user.UserLoginAndReg
import com.imprarce.android.network.utils.Constants.MINIMUM_PASSWORD_LENGTH
import com.imprarce.android.network.utils.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class GreetingViewModel @Inject constructor(
    private val networkRepository: com.imprarce.android.network.repository.user.UserRepositoryNetwork,
    private val userRepository: UserRepository,
    private val sessionManager: SessionManager
) : ViewModel() {

    private var _loginLiveData = MutableLiveData<ResponseNetwork<String>>(null)
    val loginLiveData: LiveData<ResponseNetwork<String>> = _loginLiveData

    private var _signUpLiveData = MutableLiveData<ResponseNetwork<String>>(null)
    val signUpLiveData: LiveData<ResponseNetwork<String>> = _signUpLiveData

    private var _userGetNetworkLiveData = MutableLiveData<ResponseNetwork<User>>(null)
    var userGetNetworkLiveData: LiveData<ResponseNetwork<User>> = _userGetNetworkLiveData

    private var _userGetRoomLiveData = MutableLiveData<UserDbEntity?>(null)
    var userGetRoomLiveData: LiveData<UserDbEntity?> = _userGetRoomLiveData

    private var _tokenLiveData = MutableLiveData<String>(null)
    val tokenLiveData: LiveData<String> = _tokenLiveData

    init {
        viewModelScope.launch {
            if(sessionManager.getJwtToken() != null){
                _tokenLiveData.value = sessionManager.getJwtToken()
            }
        }

        userGetNetworkLiveData.observeForever { response ->
            if (response is ResponseNetwork.Success) {
                viewModelScope.launch {

                    val email = sessionManager.getCurrentUserEmail() ?: return@launch
                    val id = response.result.idUser
                    sessionManager.setCurrentUserId(id)
                    addCurrentUserToRoomIfNotExists(email)
                }
            }
        }
    }

    fun login(email: String, password: String) = viewModelScope.launch {
        _loginLiveData.value = ResponseNetwork.Loading

        if (!isEmailValid(email)) {
            _loginLiveData.value = ResponseNetwork.Failure("Почта написана неверно")
            return@launch
        }

        if (!isPasswordValid(email)) {
            _loginLiveData.value = ResponseNetwork.Failure("Пароль написан неверно")
            return@launch
        }

        val user = UserLoginAndReg(
            email = email,
            password = password
        )

        _loginLiveData.value = networkRepository.login(user)

        val loginResponse = _loginLiveData.value

        if (loginResponse is ResponseNetwork.Success) {
            val token = loginResponse.result
            sessionManager.saveJwtToken(token)
        }

        _userGetNetworkLiveData.value = networkRepository.getUser()
    }

    fun signUp(email: String, password: String) = viewModelScope.launch {

        _signUpLiveData.value = ResponseNetwork.Loading

        if (!isEmailValid(email)) {
            _signUpLiveData.value = ResponseNetwork.Failure("Почта написана неверно")
            return@launch
        }

        if (!isPasswordValid(password)) {
            _signUpLiveData.value = ResponseNetwork.Failure("Пароль написан неверно")

            return@launch
        }

        val newUser = UserLoginAndReg(
            email = email,
            password = password
        )

        _signUpLiveData.value = networkRepository.signUp(newUser)

        val signUpResponse = _signUpLiveData.value

        if (signUpResponse is ResponseNetwork.Success) {
            val token = signUpResponse.result
            sessionManager.saveJwtToken(token)
        }

        _userGetNetworkLiveData.value = networkRepository.getUser()
    }

    private suspend fun addCurrentUserToRoomIfNotExists(email: String) {
        viewModelScope.launch {
            when(val response = userRepository.getUserByEmail(email)){
                is ResponseRoom.Success -> {
                    if(response.result == null){
                        val userNetworkResponse = _userGetNetworkLiveData.value
                        if (userNetworkResponse is ResponseNetwork.Success) {
                            val user = userNetworkResponse.result
                            user.imageUrl = user.imageUrl.replace("http://", "https://")
                            if (user != null) {
                                val userDbEntity = ConverterUser().convertToDbEntity(user)
                                userRepository.insertUser(userDbEntity)
                                _userGetRoomLiveData.value = userDbEntity
                                ResponseRoom.Success(Unit)
                            }
                        }
                    } else {
                        _userGetRoomLiveData.value = response.result
                    }
                    ResponseRoom.Success(Unit)
                }
                is ResponseRoom.Failure -> {
                }
                is ResponseRoom.Loading -> {
                    ResponseRoom.Loading
                }
            }
        }
    }

    private fun isEmailValid(email: String): Boolean {
        var regex =
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}\\@[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}(\\.[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25})+"
        val pattern = Pattern.compile(regex)
        return (email.isNotEmpty() && pattern.matcher(email).matches())
    }

    private fun isPasswordValid(password: String): Boolean {
        return (password.length >= MINIMUM_PASSWORD_LENGTH)
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("GreetingViewModel", "GreetingViewModel destroyed")
    }
}