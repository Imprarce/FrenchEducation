package com.imprarce.android.local.task_specially_ielts.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TaskSpeciallyIeltsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TaskSpeciallyIeltsDbEntity)

    @Query("SELECT * FROM task_specially_ielts")
    suspend fun getAllTasks(): List<TaskSpeciallyIeltsDbEntity>

}