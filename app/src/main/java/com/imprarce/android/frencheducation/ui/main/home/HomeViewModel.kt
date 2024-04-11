package com.imprarce.android.frencheducation.ui.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imprarce.android.frencheducation.data.db.ResponseRoom
import com.imprarce.android.frencheducation.data.db.module.ModuleListItem
import com.imprarce.android.frencheducation.data.db.module.room.ModuleRepository
import com.imprarce.android.frencheducation.data.db.module_tasks.room.ModuleTasksRepository
import com.imprarce.android.frencheducation.data.db.module_progress.room.ModuleProgressRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val moduleRepository: ModuleRepository,
    private val moduleProgressRepository: ModuleProgressRepository,
    private val moduleTasksRepository: ModuleTasksRepository
) : ViewModel() {
    private val _moduleListItems = MutableLiveData<List<ModuleListItem>>()
    val moduleListItems: LiveData<List<ModuleListItem>> = _moduleListItems

    fun loadModules(userId: String) {
        viewModelScope.launch {
            updateModuleProgress(userId)
            when (val resultModule = moduleRepository.getAllModules()) {
                is ResponseRoom.Success -> {
                    val moduleList = resultModule.result
                    val progressListResult = moduleProgressRepository.getModuleProgressByUserId(userId)
                    when (progressListResult) {
                        is ResponseRoom.Success -> {
                            val progressList = progressListResult.result
                            val modulesWithProgress = moduleList.map { module ->
                                val moduleProgress = progressList.find { it.id_module == module.id_module }?.moduleProgress ?: 0
                                ModuleListItem(module, moduleProgress)
                            }
                            _moduleListItems.value = modulesWithProgress
                        }
                        is ResponseRoom.Failure -> TODO()
                        ResponseRoom.Loading -> TODO()
                    }
                }
                is ResponseRoom.Failure -> TODO()
                ResponseRoom.Loading -> TODO()
            }
        }
    }

    private suspend fun updateModuleProgress(userId: String) {
        moduleRepository.getAllModules().let { result ->
            when (result) {
                is ResponseRoom.Success -> {
                    val moduleList = result.result
                    moduleList.forEach { module ->
                        moduleProgressRepository.updateModuleProgress(userId, module.id_module)
                    }
                }
                is ResponseRoom.Failure -> TODO()
                ResponseRoom.Loading -> TODO()
            }
        }
    }

}