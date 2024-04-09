package com.imprarce.android.frencheducation.ui.main.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imprarce.android.frencheducation.data.api.repository.FirebaseRepository
import com.imprarce.android.frencheducation.data.db.module.ModuleListItem
import com.imprarce.android.frencheducation.data.db.progress.room.ModuleDbEntity
import com.imprarce.android.frencheducation.data.db.progress.room.ModuleRepository
import com.imprarce.android.frencheducation.data.db.user.room.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val moduleRepository: ModuleRepository,
    private val firebaseRepository: FirebaseRepository
) : ViewModel() {
    private val _moduleListItems = MutableLiveData<List<ModuleListItem>>()
    val moduleListItems: LiveData<List<ModuleListItem>> = _moduleListItems

    private val _userId = MutableLiveData<String?>()
    val userId: LiveData<String?> = _userId

    private val _userPhotoUrl = MutableLiveData<String>()
    val userPhotoUrl: LiveData<String>
        get() = _userPhotoUrl

    init {
        getUserPhotoUrl()
    }

    fun setUserId() {
        val idUser = firebaseRepository.currentUser?.uid
        if(idUser != null)  _userId.value = idUser
    }
    private fun getUserPhotoUrl() {
        viewModelScope.launch {
            val url = firebaseRepository.getPhotoUrl()
            _userPhotoUrl.postValue(url)
        }
    }
    init {
        loadModules()
    }

    private fun loadModules() {
        viewModelScope.launch {
            val modules = moduleRepository.getAllModules()
            _moduleListItems.value = modules.map { ModuleListItem(it, calculateProgress(it)) }
            Log.d("MainViewModel", "$modules")
        }
    }

    private fun calculateProgress(module: ModuleDbEntity): Int {
        return 0
    }
}