package com.imprarce.android.local.task_completed

import com.imprarce.android.local.ResponseRoom
import com.imprarce.android.local.task_completed.room.TaskCompletedDao
import com.imprarce.android.local.task_completed.room.TaskCompletedDbEntity
import com.imprarce.android.local.task_completed.room.TaskCompletedRepository

class TaskCompletedRepositoryImpl(private val taskCompletedDao: TaskCompletedDao) :
    TaskCompletedRepository {
    override suspend fun getAllTaskCompleted(): ResponseRoom<List<TaskCompletedDbEntity>> {
        return try {
            val result = taskCompletedDao.getAllTaskCompleted()
            ResponseRoom.Success(result)
        } catch (e: Exception) {
            ResponseRoom.Failure(e)
        }
    }

    override suspend fun insertTaskCompleted(taskCompletedDbEntity: TaskCompletedDbEntity): ResponseRoom<Unit> {
        return try {
            taskCompletedDao.insertTaskCompleted(taskCompletedDbEntity)
            ResponseRoom.Success(Unit)
        } catch (e: Exception) {
            ResponseRoom.Failure(e)
        }
    }

    override suspend fun getCompletedTasksForUser(userId: String): ResponseRoom<List<TaskCompletedDbEntity>> {
        return try {
            val result = taskCompletedDao.getCompletedTasksForUser(userId)
            ResponseRoom.Success(result)
        } catch (e: Exception) {
            ResponseRoom.Failure(e)
        }
    }
}