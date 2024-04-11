package com.imprarce.android.frencheducation.data.db.module

import com.imprarce.android.frencheducation.data.db.module_progress.room.ModuleDbEntity

data class ModuleListItem(
    val module: ModuleDbEntity,
    val progress: Int
)