package com.imprarce.android.frencheducation.data.db.module_tasks

import com.imprarce.android.frencheducation.data.db.progress.room.ModuleTasksDao
import com.imprarce.android.frencheducation.data.db.progress.room.ModuleTasksDbEntity
import com.imprarce.android.frencheducation.data.db.progress.room.ModuleTasksRepository

class ModuleTasksRepositoryImpl(private val moduleTaskDao: ModuleTasksDao) : ModuleTasksRepository {
    override suspend fun getAllModuleTasks(): List<ModuleTasksDbEntity> {
        return moduleTaskDao.getAllModuleTasks()
    }

    override suspend fun insertModuleTask(moduleTask: ModuleTasksDbEntity) {
        moduleTaskDao.insertModuleTask(moduleTask)
    }

}