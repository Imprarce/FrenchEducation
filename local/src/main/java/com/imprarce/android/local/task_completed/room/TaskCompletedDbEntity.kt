package com.imprarce.android.local.task_completed.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.imprarce.android.local.task.room.TaskDbEntity
import com.imprarce.android.local.user.room.UserDbEntity

@Entity(tableName = "task_completed",
    foreignKeys = [
        ForeignKey(entity = UserDbEntity::class, parentColumns = ["id_user"], childColumns = ["id_user"]),
        ForeignKey(entity = TaskDbEntity::class, parentColumns = ["id_task"], childColumns = ["id_task"])
    ],
    primaryKeys = ["id_user", "id_task"])
data class TaskCompletedDbEntity(
    @ColumnInfo(name = "id_user") val id_user: String,
    @ColumnInfo(name = "id_task") val id_task: Int
)