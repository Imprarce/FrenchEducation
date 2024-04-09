package com.imprarce.android.frencheducation.data.db.task_completed

import com.imprarce.android.frencheducation.data.db.progress.room.ModuleTasksDao
import com.imprarce.android.frencheducation.data.db.progress.room.ModuleTasksDbEntity
import com.imprarce.android.frencheducation.data.db.progress.room.ModuleTasksRepository
import com.imprarce.android.frencheducation.data.db.task.room.TaskDbEntity
import com.imprarce.android.frencheducation.data.db.task_completed.room.TaskCompletedDao
import com.imprarce.android.frencheducation.data.db.task_completed.room.TaskCompletedDbEntity
import com.imprarce.android.frencheducation.data.db.task_completed.room.TaskCompletedRepository

class TaskCompletedRepositoryImpl(private val taskCompletedDao: TaskCompletedDao) : TaskCompletedRepository {
    override suspend fun getAllTaskCompleted() : List<TaskCompletedDbEntity> {
        return taskCompletedDao.getAllTaskCompleted()
    }

    override suspend fun insertTaskCompleted(taskCompletedDbEntity: TaskCompletedDbEntity) {
        taskCompletedDao.insertTaskCompleted(taskCompletedDbEntity)
    }

    override suspend fun getCompletedTasksForUser(userId: String): List<TaskCompletedDbEntity> {
        return taskCompletedDao.getCompletedTasksForUser(userId)
    }

}