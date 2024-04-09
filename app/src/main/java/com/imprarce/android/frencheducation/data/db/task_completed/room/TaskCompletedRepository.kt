package com.imprarce.android.frencheducation.data.db.task_completed.room

import com.imprarce.android.frencheducation.data.db.task.room.TaskDbEntity

interface TaskCompletedRepository {
    suspend fun getAllTaskCompleted() : List<TaskCompletedDbEntity>
    suspend fun insertTaskCompleted(taskCompletedDbEntity: TaskCompletedDbEntity)
    suspend fun getCompletedTasksForUser(userId: String) : List<TaskCompletedDbEntity>
}