package com.imprarce.android.network.repository.task_completed

import com.imprarce.android.network.ResponseNetwork
import com.imprarce.android.network.api.NetworkApi
import com.imprarce.android.network.model.module_progress.ModuleProgress
import com.imprarce.android.network.model.task_completed.TaskCompleted
import com.imprarce.android.network.utils.SessionManager
import javax.inject.Inject

class TaskCompletedNetworkRepositoryImpl @Inject constructor(
    private val taskApi: NetworkApi,
    private val sessionManager: SessionManager
) : TaskCompletedNetworkRepository {

    override suspend fun getTaskCompleted(taskId: Int): ResponseNetwork<TaskCompleted?> {
        return try {
            val userId = sessionManager.getCurrentUserId()

            val response = taskApi.getTaskCompleted(userId!!, taskId)
            if (response.isSuccessful) {
                ResponseNetwork.Success(response.body())
            } else {
                ResponseNetwork.Failure("Задание не найдено")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            ResponseNetwork.Failure(e.message ?: "Возникла какая-то ошибка")
        }
    }

    override suspend fun getCompletedTasksForUser(): ResponseNetwork<List<TaskCompleted>?> {
        return try {
            val userId = sessionManager.getCurrentUserId()

            val response = taskApi.getCompletedTasksForUser(userId!!)
            if (response.isSuccessful) {
                ResponseNetwork.Success(response.body())
            } else {
                ResponseNetwork.Failure("Задания не найдены")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            ResponseNetwork.Failure(e.message ?: "Возникла какая-то ошибка")
        }
    }

    override suspend fun createTaskCompleted(taskId: Int): ResponseNetwork<Unit> {
        return try {
            val userId = sessionManager.getCurrentUserId()

            val taskCompleted = TaskCompleted(
                idUser = userId!!,
                idTask = taskId
            )

            val response = taskApi.createTaskCompleted(taskCompleted)
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