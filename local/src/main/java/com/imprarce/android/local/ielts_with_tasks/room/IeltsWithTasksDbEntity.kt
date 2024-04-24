package com.imprarce.android.local.ielts_with_tasks.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.imprarce.android.local.ielts.room.IeltsDbEntity
import com.imprarce.android.local.task_specially_ielts.room.TaskSpeciallyIeltsDbEntity


@Entity(tableName = "ielts_with_tasks",
    primaryKeys = ["id_ielts", "id_specially_task"],
    foreignKeys = [
        ForeignKey(
            entity = IeltsDbEntity::class,
            parentColumns = ["id_ielts"],
            childColumns = ["id_ielts"]
        ),
        ForeignKey(
            entity = TaskSpeciallyIeltsDbEntity::class,
            parentColumns = ["id_specially_task"],
            childColumns = ["id_specially_task"]
        )
    ]
)
data class IeltsWithTasksDbEntity(
    @ColumnInfo(name = "id_ielts") val idIelts: Int,
    @ColumnInfo(name = "id_specially_task") val idSpeciallyTask: Int
)
