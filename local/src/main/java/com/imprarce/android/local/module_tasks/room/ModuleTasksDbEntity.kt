package com.imprarce.android.frencheducation.data.db.module_progress.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.imprarce.android.local.task.room.TaskDbEntity

@Entity(tableName = "module_tasks",
    foreignKeys = [
        ForeignKey(entity = ModuleDbEntity::class, parentColumns = ["id_module"], childColumns = ["id_module"]),
        ForeignKey(entity = TaskDbEntity::class, parentColumns = ["id_task"], childColumns = ["id_task"])
    ],
    primaryKeys = ["id_module", "id_task"])
data class ModuleTasksDbEntity(
    @ColumnInfo(name = "id_module") val id_module: Int,
    @ColumnInfo(name = "id_task") val id_task: Int
)