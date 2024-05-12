package com.imprarce.android.network.model.module_progress

import com.imprarce.android.network.model.module.Module

data class ModuleProgressListItem(
    val module: Module,
    val progress: Int
)
