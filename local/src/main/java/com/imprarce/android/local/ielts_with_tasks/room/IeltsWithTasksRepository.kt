package com.imprarce.android.local.ielts_with_tasks.room

interface IeltsWithTasksRepository {
    suspend fun insertIeltsWithTask(ieltsWithTasks: IeltsWithTasksDbEntity)
    suspend fun deleteIeltsWithTask(ieltsWithTasks: IeltsWithTasksDbEntity)
    suspend fun getTasksForIelts(idIelts: Int): List<IeltsWithTasksDbEntity>
}