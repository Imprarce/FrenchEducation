package com.imprarce.android.frencheducation.data.db.module_tasks

import com.imprarce.android.frencheducation.data.db.progress.room.ModuleTasksDao
import com.imprarce.android.frencheducation.data.db.progress.room.ModuleTasksDbEntity
import com.imprarce.android.frencheducation.data.db.progress.room.ModuleTasksRepository
import com.imprarce.android.frencheducation.data.db.task.room.TaskDbEntity

class ModuleTasksRepositoryImpl(private val moduleTaskDao: ModuleTasksDao) : ModuleTasksRepository {
    override suspend fun getAllModuleTasks(): List<ModuleTasksDbEntity> {
        return moduleTaskDao.getAllModuleTasks()
    }

    override suspend fun insertModuleTask(moduleTask: ModuleTasksDbEntity) {
        moduleTaskDao.insertModuleTask(moduleTask)
    }

    override suspend fun getTaskIdsForModule(moduleId: Int): List<Int> {
        return moduleTaskDao.getTaskIdsForModule(moduleId)
    }

    override suspend fun getTasksByIds(taskIds: List<Int>): List<TaskDbEntity> {
        return moduleTaskDao.getTasksByIds(taskIds)
    }

}