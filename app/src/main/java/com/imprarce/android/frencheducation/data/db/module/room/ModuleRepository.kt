package com.imprarce.android.frencheducation.data.db.progress.room

interface ModuleRepository {
    suspend fun getAllModules(): List<ModuleDbEntity>
    suspend fun insertModule(module: ModuleDbEntity)
}