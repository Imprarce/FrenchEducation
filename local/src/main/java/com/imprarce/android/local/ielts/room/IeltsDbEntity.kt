package com.imprarce.android.local.ielts.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "ielts")
data class IeltsDbEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_ielts") val id: Int = 0,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "description") val description: String
)