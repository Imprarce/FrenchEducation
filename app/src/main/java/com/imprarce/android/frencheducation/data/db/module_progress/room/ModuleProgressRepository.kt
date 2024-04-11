package com.imprarce.android.frencheducation.data.db.module_progress.room

import com.imprarce.android.frencheducation.data.db.ResponseRoom

interface ModuleProgressRepository {
    suspend fun getAllModuleProgress(): ResponseRoom<List<ModuleProgressDbEntity>>
    suspend fun getModuleProgressByUserId(userId: String): ResponseRoom<List<ModuleProgressDbEntity>>
    suspend fun updateModuleProgress(userId: String, moduleId: Int): ResponseRoom<Unit>
}