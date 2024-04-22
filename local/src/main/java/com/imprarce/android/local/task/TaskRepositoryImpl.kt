package com.imprarce.android.local.task

import com.imprarce.android.local.ResponseRoom
import com.imprarce.android.local.task.room.TaskDao
import com.imprarce.android.local.task.room.TaskDbEntity
import com.imprarce.android.local.task.room.TaskRepository

class TaskRepositoryImpl(private val taskDao: TaskDao) : TaskRepository {
    override suspend fun getAllTasks(): ResponseRoom<List<TaskDbEntity>> {
        return try {
            val result = taskDao.getAllTasks()
            ResponseRoom.Success(result)
        } catch (e: Exception) {
            ResponseRoom.Failure(e)
        }
    }

    override suspend fun insertTask(task: TaskDbEntity): ResponseRoom<Unit> {
        return try {
            taskDao.insertTask(task)
            ResponseRoom.Success(Unit)
        } catch (e: Exception) {
            ResponseRoom.Failure(e)
        }
    }

    override suspend fun getTaskById(taskId: Int): ResponseRoom<TaskDbEntity?> {
        return try {
            val result = taskDao.getTaskById(taskId)
            ResponseRoom.Success(result)
        } catch (e: Exception) {
            ResponseRoom.Failure(e)
        }
    }
}