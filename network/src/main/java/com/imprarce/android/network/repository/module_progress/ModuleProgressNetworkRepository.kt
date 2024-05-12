package com.imprarce.android.network.repository.module_progress

import com.imprarce.android.network.ResponseNetwork
import com.imprarce.android.network.SimpleResponse
import com.imprarce.android.network.model.module_progress.ModuleProgress

interface ModuleProgressNetworkRepository {
    suspend fun getModulesProgress(idModule: Int): ResponseNetwork<ModuleProgress?>
    suspend fun setModuleProgress(idModule: Int, progress: Int): ResponseNetwork<Unit>
    suspend fun createModuleProgress(idModule: Int, progress: Int): ResponseNetwork<Unit>
}