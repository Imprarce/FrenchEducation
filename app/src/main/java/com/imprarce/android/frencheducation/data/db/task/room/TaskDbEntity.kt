package com.imprarce.android.frencheducation.data.db.task.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task")
data class TaskDbEntity(
    @PrimaryKey(autoGenerate = true) var id_task: Int = 0,
    @ColumnInfo(name = "task_name") var taskName: String,
    @ColumnInfo(name = "video_file") var videoFile: String?,
    @ColumnInfo(name = "audio_file") var audioFile: String?,
    @ColumnInfo(name = "exercise") var exercise: String,
    @ColumnInfo(name = "answer") var answer: String
)