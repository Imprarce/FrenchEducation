package com.imprarce.android.network.repository.module

import com.imprarce.android.network.ResponseNetwork
import com.imprarce.android.network.api.NetworkApi
import com.imprarce.android.network.model.module.Module
import com.imprarce.android.network.model.module_tasks.ModuleTasks
import javax.inject.Inject

class ModuleNetworkRepositoryImpl @Inject constructor(
    private val moduleTasksApi: NetworkApi
) : ModuleNetworkRepository {

    override suspend fun getModules(): ResponseNetwork<List<Module>> {
        return try {
            val response = moduleTasksApi.getModules()
            if (response.isSuccessful) {
                val module = response.body() ?: emptyList()
                ResponseNetwork.Success(module)
            } else {
                ResponseNetwork.Failure(response.message())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            ResponseNetwork.Failure(e.message ?: "Возникла какая-то ошибка")
        }
    }
}