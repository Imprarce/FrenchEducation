package com.imprarce.android.frencheducation.data.db.task.room

import com.imprarce.android.frencheducation.data.db.ResponseRoom

interface TaskRepository {
    suspend fun getAllTasks(): ResponseRoom<List<TaskDbEntity>>
    suspend fun insertTask(task: TaskDbEntity): ResponseRoom<Unit>
    suspend fun getTaskById(taskId: Int): ResponseRoom<TaskDbEntity?>
}