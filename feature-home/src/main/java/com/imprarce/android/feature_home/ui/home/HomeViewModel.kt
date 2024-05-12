package com.imprarce.android.feature_home.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imprarce.android.feature_home.ui.helpers.Converter
import com.imprarce.android.local.ResponseRoom
import com.imprarce.android.local.module.ModuleListItem
import com.imprarce.android.local.module.room.ModuleRepository
import com.imprarce.android.local.module_progress.room.ModuleProgressRepository
import com.imprarce.android.network.ResponseNetwork
import com.imprarce.android.network.model.module_progress.ModuleProgressListItem
import com.imprarce.android.network.repository.module.ModuleNetworkRepository
import com.imprarce.android.network.repository.module_progress.ModuleProgressNetworkRepository
import com.imprarce.android.network.utils.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val moduleRepository: ModuleRepository,
    private val moduleProgressRepository: ModuleProgressRepository,
    private val moduleNetworkRepository: ModuleNetworkRepository,
    private val moduleProgressNetworkRepository: ModuleProgressNetworkRepository,
    private val sessionManager: SessionManager
) : ViewModel() {
    private val _moduleListItems = MutableLiveData<List<ModuleListItem>>()
    val moduleListItems: LiveData<List<ModuleListItem>> = _moduleListItems

    private val _moduleListNetwork = MutableLiveData<List<ModuleProgressListItem>>()
    val moduleListNetwork: LiveData<List<ModuleProgressListItem>> = _moduleListNetwork

    fun loadModulesFromRoom(userId: String) {
        viewModelScope.launch {
            updateModuleProgressRoom(userId)
            when (val resultModule = moduleRepository.getAllModules()) {
                is ResponseRoom.Success -> {
                    val moduleList = resultModule.result
                    val progressListResult =
                        moduleProgressRepository.getModuleProgressByUserId(userId)
                    when (progressListResult) {
                        is ResponseRoom.Success -> {
                            val progressList = progressListResult.result
                            val modulesWithProgress = moduleList.map { module ->
                                val moduleProgress =
                                    progressList.find { it.id_module == module.id_module }?.moduleProgress
                                        ?: 0
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


    // TODO: СДЕЛАТЬ ЛОКАЛЬНУЮ ВЕРСИЮ
    fun loadModulesFromNetwork() {
        viewModelScope.launch {

            val modulesResponse = moduleNetworkRepository.getModules()
            when (modulesResponse) {
                is ResponseNetwork.Success -> {
                    val moduleList = modulesResponse.result
                    val modulesWithProgress = mutableListOf<ModuleProgressListItem>()


                    moduleList.forEach { module ->
                        val progressResponse =
                            moduleProgressNetworkRepository.getModulesProgress(module.idModule)
                        when (progressResponse) {
                            is ResponseNetwork.Success -> {
                                val moduleProgress = progressResponse.result

                                if (module.idModule == moduleProgress!!.idModule) {
                                    modulesWithProgress.add(
                                        ModuleProgressListItem(module, moduleProgress.progress)
                                    )
                                }
                            }
                            is ResponseNetwork.Failure -> {
                                if(progressResponse.exception.equals("Прогресс не найден")){
                                    moduleProgressNetworkRepository.createModuleProgress(
                                        module.idModule,
                                        0
                                    )
                                    modulesWithProgress.add(
                                        ModuleProgressListItem(module, 0)
                                    )
                                }
                            }
                            is ResponseNetwork.Loading -> {

                            }
                        }
                    }
                    _moduleListNetwork.value = modulesWithProgress
                    _moduleListItems.value = modulesWithProgress.map { moduleProgressItem ->
                        ModuleListItem(
                            Converter.toModuleDbEntity(moduleProgressItem.module),
                            moduleProgressItem.progress
                        )
                    }
                }
                is ResponseNetwork.Failure -> {

                }
                is ResponseNetwork.Loading -> {

                }
            }
        }
    }

    private suspend fun updateModuleProgressRoom(userId: String) {
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