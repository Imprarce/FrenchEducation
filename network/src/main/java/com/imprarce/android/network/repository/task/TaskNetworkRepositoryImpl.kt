package com.imprarce.android.network.repository.task

import com.imprarce.android.network.ResponseNetwork
import com.imprarce.android.network.api.NetworkApi
import com.imprarce.android.network.model.module_tasks.ModuleTasks
import com.imprarce.android.network.model.task.Task
import javax.inject.Inject

class TaskNetworkRepositoryImpl @Inject constructor(
    private val moduleTasksApi: NetworkApi
) : TaskNetworkRepository {

    override suspend fun getTasks(listTask: List<Int>): ResponseNetwork<List<Task>> {
        return try {
            val response = moduleTasksApi.getTask(listTask)
            if (response.isSuccessful) {
                val tasks = response.body() ?: emptyList()
                ResponseNetwork.Success(tasks)
            } else {
                ResponseNetwork.Failure(response.message())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            ResponseNetwork.Failure(e.message ?: "Возникла какая-то ошибка")
        }
    }

    override suspend fun getTaskById(task_id: Int): ResponseNetwork<Task> {
        return try {
            val response = moduleTasksApi.getTaskById(task_id)
            if (response.isSuccessful) {
                ResponseNetwork.Success(response.body()!!)
            } else {
                ResponseNetwork.Failure(response.message())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            ResponseNetwork.Failure(e.message ?: "Возникла какая-то ошибка")
        }
    }
}