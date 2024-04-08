package com.imprarce.android.frencheducation.data.db.task

import com.imprarce.android.frencheducation.data.db.task.room.TaskDao
import com.imprarce.android.frencheducation.data.db.task.room.TaskDbEntity
import com.imprarce.android.frencheducation.data.db.task.room.TaskRepository

class TaskRepositoryImpl(private val taskDao: TaskDao) : TaskRepository {
    override suspend fun getAllTasks(): List<TaskDbEntity> {
        return taskDao.getAllTasks()
    }

    override suspend fun insertTask(task: TaskDbEntity) {
        taskDao.insertTask(task)
    }

    override suspend fun getTaskById(taskId: Int): TaskDbEntity {
        return taskDao.getTaskById(taskId)
    }

}