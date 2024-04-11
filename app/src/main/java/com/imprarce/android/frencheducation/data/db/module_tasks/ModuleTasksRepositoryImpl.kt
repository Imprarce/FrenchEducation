package com.imprarce.android.frencheducation.data.db.module_tasks

import com.imprarce.android.frencheducation.data.db.ResponseRoom
import com.imprarce.android.frencheducation.data.db.module_progress.room.ModuleTasksDao
import com.imprarce.android.frencheducation.data.db.module_progress.room.ModuleTasksDbEntity
import com.imprarce.android.frencheducation.data.db.module_tasks.room.ModuleTasksRepository
import com.imprarce.android.frencheducation.data.db.task.room.TaskDbEntity

class ModuleTasksRepositoryImpl(private val moduleTaskDao: ModuleTasksDao) : ModuleTasksRepository {
    override suspend fun getAllModuleTasks(): ResponseRoom<List<ModuleTasksDbEntity>> {
        return try {
            val result = moduleTaskDao.getAllModuleTasks()
            ResponseRoom.Success(result)
        } catch (e: Exception) {
            ResponseRoom.Failure(e)
        }
    }

    override suspend fun insertModuleTask(moduleTask: ModuleTasksDbEntity): ResponseRoom<Unit> {
        return try {
            moduleTaskDao.insertModuleTask(moduleTask)
            ResponseRoom.Success(Unit)
        } catch (e: Exception) {
            ResponseRoom.Failure(e)
        }
    }

    override suspend fun getTaskIdsForModule(moduleId: Int): ResponseRoom<List<Int>> {
        return try {
            val result = moduleTaskDao.getTaskIdsForModule(moduleId)
            ResponseRoom.Success(result)
        } catch (e: Exception) {
            ResponseRoom.Failure(e)
        }
    }

    override suspend fun getTasksByIds(taskIds: List<Int>): ResponseRoom<List<TaskDbEntity>> {
        return try {
            val result = moduleTaskDao.getTasksByIds(taskIds)
            ResponseRoom.Success(result)
        } catch (e: Exception) {
            ResponseRoom.Failure(e)
        }
    }
}