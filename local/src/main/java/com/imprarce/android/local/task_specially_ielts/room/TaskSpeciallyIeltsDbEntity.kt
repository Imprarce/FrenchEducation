package com.imprarce.android.local.task_specially_ielts.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "task_specially_ielts")
data class TaskSpeciallyIeltsDbEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_specially_task") val idSpeciallyTask: Int = 0,
    @ColumnInfo(name = "task_name")  val taskName: String,
    @ColumnInfo(name = "video_file")  val videoFile: String?,
    @ColumnInfo(name = "audio_file")  val audioFile: String?,
    @ColumnInfo(name = "exercise")  val exercise: String?,
    @ColumnInfo(name = "answer")  val answer: String,
    @ColumnInfo(name = "type")  val type: String
)