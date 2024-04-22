package com.imprarce.android.local.task_completed.room

import com.imprarce.android.local.ResponseRoom

interface TaskCompletedRepository {
    suspend fun getAllTaskCompleted(): ResponseRoom<List<TaskCompletedDbEntity>>
    suspend fun insertTaskCompleted(taskCompletedDbEntity: TaskCompletedDbEntity): ResponseRoom<Unit>
    suspend fun getCompletedTasksForUser(userId: String): ResponseRoom<List<TaskCompletedDbEntity>>
}