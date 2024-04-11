package com.imprarce.android.frencheducation.data.db.module.room

import com.imprarce.android.frencheducation.data.db.ResponseRoom
import com.imprarce.android.frencheducation.data.db.module_progress.room.ModuleDbEntity

interface ModuleRepository {
    suspend fun getAllModules(): ResponseRoom<List<ModuleDbEntity>>
    suspend fun insertModule(module: ModuleDbEntity): ResponseRoom<Unit>
}