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
import com.imprarce.android.frencheducation.ui.MainViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val moduleRepository: ModuleRepository,
) : ViewModel() {
    private val _moduleListItems = MutableLiveData<List<ModuleListItem>>()
    val moduleListItems: LiveData<List<ModuleListItem>> = _moduleListItems

    init {
        loadModules()
    }

    private fun loadModules() {
        viewModelScope.launch {
            val modules = moduleRepository.getAllModules()
            _moduleListItems.value = modules.map { ModuleListItem(it, calculateProgress(it)) }
        }
    }

    private fun calculateProgress(module: ModuleDbEntity): Int {
        return 0
    }
}