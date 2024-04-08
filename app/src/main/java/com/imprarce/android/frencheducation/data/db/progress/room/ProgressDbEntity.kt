package com.imprarce.android.frencheducation.data.db.progress.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.imprarce.android.frencheducation.data.db.user.room.UserDbEntity

@Entity(tableName = "progress",
    foreignKeys = [
        ForeignKey(entity = UserDbEntity::class, parentColumns = ["id_user"], childColumns = ["id_user"], onDelete = ForeignKey.CASCADE),
        ForeignKey(entity = ModuleDbEntity::class, parentColumns = ["id_module"], childColumns = ["id_module"], onDelete = ForeignKey.CASCADE)
    ],
    primaryKeys = ["id_user", "id_module"])
data class ProgressDbEntity(
    @ColumnInfo(name = "id_user") var id_user: Int,
    @ColumnInfo(name = "id_module") var id_module: Int,
    @ColumnInfo(name = "module_progress") var moduleProgress: Int?
)