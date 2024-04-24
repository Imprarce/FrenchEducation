package com.imprarce.android.local.task_specially_ielts

import com.imprarce.android.local.task_specially_ielts.room.TaskSpeciallyIeltsDao
import com.imprarce.android.local.task_specially_ielts.room.TaskSpeciallyIeltsDbEntity
import com.imprarce.android.local.task_specially_ielts.room.TaskSpeciallyIeltsRepository

class TaskSpeciallyIeltsRepositoryImpl(private val taskSpeciallyIeltsDao: TaskSpeciallyIeltsDao) :
    TaskSpeciallyIeltsRepository {
    override suspend fun insertTask(task: TaskSpeciallyIeltsDbEntity) {
        taskSpeciallyIeltsDao.insertTask(task)
    }

    override suspend fun getAllTasks(): List<TaskSpeciallyIeltsDbEntity> {
        return taskSpeciallyIeltsDao.getAllTasks()
    }
}