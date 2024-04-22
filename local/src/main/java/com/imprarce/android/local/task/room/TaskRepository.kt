package com.imprarce.android.local.task.room

import com.imprarce.android.local.ResponseRoom

interface TaskRepository {
    suspend fun getAllTasks(): ResponseRoom<List<TaskDbEntity>>
    suspend fun insertTask(task: TaskDbEntity): ResponseRoom<Unit>
    suspend fun getTaskById(taskId: Int): ResponseRoom<TaskDbEntity?>
}