package com.imprarce.android.frencheducation.data.db.progress.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "module")
data class ModuleDbEntity(
    @PrimaryKey(autoGenerate = true) var id_module: Int = 0,
    @ColumnInfo(name = "image_url") var imageUrl: String?,
    @ColumnInfo(name = "module_name") var moduleName: String,
    @ColumnInfo(name = "module_level") var moduleLevel: Int
)