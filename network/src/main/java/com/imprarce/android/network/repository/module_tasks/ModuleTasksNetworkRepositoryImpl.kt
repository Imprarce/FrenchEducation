package com.imprarce.android.network.repository.module_tasks

import com.imprarce.android.network.ResponseNetwork
import com.imprarce.android.network.api.NetworkApi
import com.imprarce.android.network.model.dictionary.DictionaryWord
import com.imprarce.android.network.model.module_tasks.ModuleTasks
import javax.inject.Inject

class ModuleTasksNetworkRepositoryImpl @Inject constructor(
    private val moduleTasksApi: NetworkApi
) : ModuleTasksNetworkRepository {

    override suspend fun getModuleTasks(moduleId: Int): ResponseNetwork<List<ModuleTasks>> {
        return try {
            val response = moduleTasksApi.getModuleTasks(moduleId)
            if (response.isSuccessful) {
                val moduleTasks = response.body() ?: emptyList()
                ResponseNetwork.Success(moduleTasks)
            } else {
                ResponseNetwork.Failure(response.message())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            ResponseNetwork.Failure(e.message ?: "Возникла какая-то ошибка")
        }
    }
}