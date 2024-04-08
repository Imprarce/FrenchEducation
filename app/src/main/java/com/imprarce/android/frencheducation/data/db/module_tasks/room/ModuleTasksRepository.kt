package com.imprarce.android.frencheducation.data.db.progress.room

import com.imprarce.android.frencheducation.data.db.task.room.TaskDbEntity

interface ModuleTasksRepository {
    suspend fun getAllModuleTasks(): List<ModuleTasksDbEntity>
    suspend fun insertModuleTask(moduleTask: ModuleTasksDbEntity)
    suspend fun getTaskIdsForModule(moduleId: Int): List<Int>
    suspend fun getTasksByIds(taskIds: List<Int>): List<TaskDbEntity>
}