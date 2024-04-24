package com.imprarce.android.local.ielts_with_tasks

import com.imprarce.android.local.ielts_with_tasks.room.IeltsWithTasksDao
import com.imprarce.android.local.ielts_with_tasks.room.IeltsWithTasksDbEntity
import com.imprarce.android.local.ielts_with_tasks.room.IeltsWithTasksRepository

class IeltsWithTasksRepositoryImpl(private val dao: IeltsWithTasksDao) : IeltsWithTasksRepository {
    override suspend fun insertIeltsWithTask(ieltsWithTasks: IeltsWithTasksDbEntity) {
        dao.insertIeltsWithTask(ieltsWithTasks)
    }

    override suspend fun deleteIeltsWithTask(ieltsWithTasks: IeltsWithTasksDbEntity) {
        dao.deleteIeltsWithTask(ieltsWithTasks)
    }

    override suspend fun getTasksForIelts(idIelts: Int): List<IeltsWithTasksDbEntity> {
        return dao.getTasksForIelts(idIelts)
    }
}