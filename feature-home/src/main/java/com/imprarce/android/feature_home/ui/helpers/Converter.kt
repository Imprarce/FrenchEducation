package com.imprarce.android.feature_home.ui.helpers

import com.imprarce.android.frencheducation.data.db.module_progress.room.ModuleDbEntity
import com.imprarce.android.local.task.room.TaskDbEntity
import com.imprarce.android.network.model.module.Module
import com.imprarce.android.network.model.task.Task

object Converter {
    fun toModuleDbEntity(module: Module): ModuleDbEntity {
        return ModuleDbEntity(
            id_module = module.idModule,
            imageUrl = module.imageUrl,
            moduleName = module.moduleName,
            moduleLevel = module.moduleLevel
        )
    }

    fun toTaskDbEntity(task: Task): TaskDbEntity {
        return TaskDbEntity(
            id_task = task.idTask,
            taskName = task.taskName,
            videoFile = task.videoFile,
            audioFile = task.audioFile,
            exercise = task.exercise,
            answer = task.answer,
            type = task.type
        )
    }
}