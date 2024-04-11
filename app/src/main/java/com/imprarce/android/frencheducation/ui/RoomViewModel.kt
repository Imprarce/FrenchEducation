package com.imprarce.android.frencheducation.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imprarce.android.frencheducation.data.api.repository.FirebaseRepository
import com.imprarce.android.frencheducation.data.db.user.room.UserDbEntity
import com.imprarce.android.frencheducation.data.db.user.room.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoomViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val repository: FirebaseRepository
) : ViewModel() {

    private val _userFromRoom = MutableLiveData<UserDbEntity>(null)
    val userFromRoom: LiveData<UserDbEntity> = _userFromRoom

    init {
    }

//    private fun getUser() = viewModelScope.launch{
//        val currentUser = repository.currentUser
//        if (currentUser != null) {
//            val id_user = currentUser.uid
//            _userFromRoom.value = userRepository.getUserById(id_user)
//        }
//    }
}