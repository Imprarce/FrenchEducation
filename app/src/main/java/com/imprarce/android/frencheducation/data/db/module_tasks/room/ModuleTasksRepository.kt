package com.imprarce.android.frencheducation.data.db.progress.room

interface ModuleTasksRepository {
    suspend fun getAllModuleTasks(): List<ModuleTasksDbEntity>
    suspend fun insertModuleTask(moduleTask: ModuleTasksDbEntity)
}