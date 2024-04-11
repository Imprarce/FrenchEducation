package com.imprarce.android.frencheducation.data.db.module

import com.imprarce.android.frencheducation.data.db.ResponseRoom
import com.imprarce.android.frencheducation.data.db.module_progress.room.ModuleDao
import com.imprarce.android.frencheducation.data.db.module_progress.room.ModuleDbEntity
import com.imprarce.android.frencheducation.data.db.module.room.ModuleRepository

class ModuleRepositoryImpl(private val moduleDao: ModuleDao) : ModuleRepository {
    override suspend fun getAllModules(): ResponseRoom<List<ModuleDbEntity>> {
        return try {
            val result = moduleDao.getAllModules()
            ResponseRoom.Success(result)
        } catch (e: Exception) {
            ResponseRoom.Failure(e)
        }
    }

    override suspend fun insertModule(module: ModuleDbEntity): ResponseRoom<Unit> {
        return try {
            moduleDao.insertModule(module)
            ResponseRoom.Success(Unit)
        } catch (e: Exception) {
            ResponseRoom.Failure(e)
        }
    }
}