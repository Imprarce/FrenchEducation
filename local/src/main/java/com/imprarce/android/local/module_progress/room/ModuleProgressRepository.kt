package com.imprarce.android.local.module_progress.room

import com.imprarce.android.local.ResponseRoom

interface ModuleProgressRepository {
    suspend fun getAllModuleProgress(): ResponseRoom<List<ModuleProgressDbEntity>>
    suspend fun getModuleProgressByUserId(userId: String): ResponseRoom<List<ModuleProgressDbEntity>>
    suspend fun updateModuleProgress(userId: String, moduleId: Int): ResponseRoom<Unit>
}