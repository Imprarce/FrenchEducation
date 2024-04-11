package com.imprarce.android.frencheducation.data.db.task_completed.room

import com.imprarce.android.frencheducation.data.db.ResponseRoom

interface TaskCompletedRepository {
    suspend fun getAllTaskCompleted(): ResponseRoom<List<TaskCompletedDbEntity>>
    suspend fun insertTaskCompleted(taskCompletedDbEntity: TaskCompletedDbEntity): ResponseRoom<Unit>
    suspend fun getCompletedTasksForUser(userId: String): ResponseRoom<List<TaskCompletedDbEntity>>
}