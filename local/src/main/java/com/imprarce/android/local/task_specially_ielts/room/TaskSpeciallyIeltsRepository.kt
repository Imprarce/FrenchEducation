package com.imprarce.android.local.task_specially_ielts.room

interface TaskSpeciallyIeltsRepository {
    suspend fun insertTask(task: TaskSpeciallyIeltsDbEntity)
    suspend fun getAllTasks(): List<TaskSpeciallyIeltsDbEntity>
}