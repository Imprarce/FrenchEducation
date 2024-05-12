package com.imprarce.android.network.repository.module_tasks

import com.imprarce.android.network.ResponseNetwork
import com.imprarce.android.network.model.module_tasks.ModuleTasks

interface ModuleTasksNetworkRepository {
    suspend fun getModuleTasks(moduleId: Int): ResponseNetwork<List<ModuleTasks>>
}