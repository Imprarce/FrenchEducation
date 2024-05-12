package com.imprarce.android.network.repository.task_completed

import com.imprarce.android.network.ResponseNetwork
import com.imprarce.android.network.model.task_completed.TaskCompleted

interface TaskCompletedNetworkRepository {
    suspend fun getTaskCompleted(taskId: Int): ResponseNetwork<TaskCompleted?>
    suspend fun createTaskCompleted(taskId: Int): ResponseNetwork<Unit>
    suspend fun getCompletedTasksForUser(): ResponseNetwork<List<TaskCompleted>?>
}