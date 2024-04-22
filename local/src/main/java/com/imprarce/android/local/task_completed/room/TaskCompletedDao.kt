package com.imprarce.android.local.task_completed.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TaskCompletedDao {

    @Query("SELECT * FROM task_completed")
    suspend fun getAllTaskCompleted() : List<TaskCompletedDbEntity>

    @Query("SELECT * FROM task_completed WHERE id_user = :userId")
    suspend fun getCompletedTasksForUser(userId: String): List<TaskCompletedDbEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTaskCompleted(taskCompletedDbEntity: TaskCompletedDbEntity)
}