package com.imprarce.android.local.module_tasks.room

import com.imprarce.android.local.ResponseRoom
import com.imprarce.android.frencheducation.data.db.module_progress.room.ModuleTasksDbEntity
import com.imprarce.android.local.task.room.TaskDbEntity

interface ModuleTasksRepository {
    suspend fun getAllModuleTasks(): ResponseRoom<List<ModuleTasksDbEntity>>
    suspend fun insertModuleTask(moduleTask: ModuleTasksDbEntity): ResponseRoom<Unit>
    suspend fun getTaskIdsForModule(moduleId: Int): ResponseRoom<List<Int>>
    suspend fun getTasksByIds(taskIds: List<Int>): ResponseRoom<List<TaskDbEntity>>
}