package com.imprarce.android.local.task.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TaskDao {
    @Query("SELECT * FROM task")
    suspend fun getAllTasks(): List<TaskDbEntity>

    @Query("SELECT * FROM task WHERE id_task = :taskId")
    suspend fun getTaskById(taskId: Int): TaskDbEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TaskDbEntity)

}