package com.imprarce.android.frencheducation.data.db.task.room

interface TaskRepository {
    suspend fun getAllTasks(): List<TaskDbEntity>
    suspend fun insertTask(task: TaskDbEntity)
}