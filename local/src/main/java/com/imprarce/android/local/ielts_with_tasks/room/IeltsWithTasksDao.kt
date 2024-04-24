package com.imprarce.android.local.ielts_with_tasks.room

import androidx.room.*

@Dao
interface IeltsWithTasksDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIeltsWithTask(ieltsWithTasks: IeltsWithTasksDbEntity)

    @Delete
    suspend fun deleteIeltsWithTask(ieltsWithTasks: IeltsWithTasksDbEntity)

    @Query("SELECT * FROM ielts_with_tasks WHERE id_ielts = :idIelts")
    suspend fun getTasksForIelts(idIelts: Int): List<IeltsWithTasksDbEntity>
}