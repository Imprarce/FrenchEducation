package com.imprarce.android.frencheducation.data.db.module

import com.imprarce.android.frencheducation.data.db.progress.room.ModuleDbEntity

data class ModuleListItem(
    val module: ModuleDbEntity,
    val progress: Int
)