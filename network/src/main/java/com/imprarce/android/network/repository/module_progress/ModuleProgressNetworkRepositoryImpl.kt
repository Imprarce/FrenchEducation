package com.imprarce.android.network.repository.module_progress

import com.imprarce.android.network.ResponseNetwork
import com.imprarce.android.network.SimpleResponse
import com.imprarce.android.network.api.NetworkApi
import com.imprarce.android.network.model.module.Module
import com.imprarce.android.network.model.module_progress.ModuleProgress
import com.imprarce.android.network.utils.SessionManager
import javax.inject.Inject

class ModuleProgressNetworkRepositoryImpl @Inject constructor(
    private val moduleTasksApi: NetworkApi,
    private val sessionManager: SessionManager
) : ModuleProgressNetworkRepository {

    override suspend fun getModulesProgress(idModule: Int): ResponseNetwork<ModuleProgress?> {
        return try {
            val userId = sessionManager.getCurrentUserId()

            val response = moduleTasksApi.getModulesProgress(userId!!, idModule)
            if (response.isSuccessful) {
                val moduleProgress = response.body()
                ResponseNetwork.Success(moduleProgress)
            } else {
                ResponseNetwork.Failure("Прогресс не найден")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            ResponseNetwork.Failure("Прогресс не найден")
        }
    }

    override suspend fun setModuleProgress(idModule: Int, progress: Int): ResponseNetwork<Unit> {
        return try {
            val userId = sessionManager.getCurrentUserId()

            val moduleProgress = ModuleProgress(
                idUser = userId!!,
                idModule = idModule,
                progress = progress
            )

            val response = moduleTasksApi.setModuleProgress(moduleProgress)
            if (response.success) {
                ResponseNetwork.Success(Unit)
            } else {
                ResponseNetwork.Failure("Что-то пошло не так")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            ResponseNetwork.Failure("Прогресс не найден")
        }
    }

    override suspend fun createModuleProgress(idModule: Int, progress: Int): ResponseNetwork<Unit> {
        return try {
            val userId = sessionManager.getCurrentUserId()

            val moduleProgress = ModuleProgress(
                idUser = userId!!,
                idModule = idModule,
                progress = progress
            )

            val response = moduleTasksApi.createModuleProgress(moduleProgress)
            if (response.success) {
                ResponseNetwork.Success(Unit)
            } else {
                ResponseNetwork.Failure("Что-то пошло не так")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            ResponseNetwork.Failure(e.message ?: "Возникла какая-то ошибка")
        }
    }
}