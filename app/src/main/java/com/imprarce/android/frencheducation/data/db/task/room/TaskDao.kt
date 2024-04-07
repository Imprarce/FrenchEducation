package com.imprarce.android.frencheducation.data.db.task.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TaskDao {
    @Query("SELECT * FROM task")
    suspend fun getAllTasks(): List<TaskDbEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TaskDbEntity)

}