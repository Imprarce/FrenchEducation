package com.imprarce.android.network.repository.task

import com.imprarce.android.network.ResponseNetwork
import com.imprarce.android.network.model.module_tasks.ModuleTasks
import com.imprarce.android.network.model.task.Task

interface TaskNetworkRepository {
    suspend fun getTasks(listTask: List<Int>): ResponseNetwork<List<Task>>
    suspend fun getTaskById(task_id: Int): ResponseNetwork<Task>
}