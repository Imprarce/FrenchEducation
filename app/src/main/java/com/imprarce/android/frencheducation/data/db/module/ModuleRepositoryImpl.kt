package com.imprarce.android.frencheducation.data.db.module

import com.imprarce.android.frencheducation.data.db.progress.room.ModuleDao
import com.imprarce.android.frencheducation.data.db.progress.room.ModuleDbEntity
import com.imprarce.android.frencheducation.data.db.progress.room.ModuleRepository

class ModuleRepositoryImpl(private val moduleDao: ModuleDao) : ModuleRepository {
    override suspend fun getAllModules(): List<ModuleDbEntity> {
        return moduleDao.getAllModules()
    }

    override suspend fun insertModule(module: ModuleDbEntity) {
        moduleDao.insertModule(module)
    }
}