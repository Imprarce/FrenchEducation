package com.imprarce.android.frencheducation.data.db.progress.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.imprarce.android.frencheducation.data.db.task.room.TaskDbEntity

@Dao
interface ModuleTasksDao {
    @Query("SELECT * FROM module_tasks")
    suspend fun getAllModuleTasks(): List<ModuleTasksDbEntity>

    @Query("SELECT id_task FROM module_tasks WHERE id_module = :moduleId")
    suspend fun getTaskIdsForModule(moduleId: Int): List<Int>

    @Query("SELECT * FROM task WHERE id_task IN (:taskIds)")
    suspend fun getTasksByIds(taskIds: List<Int>): List<TaskDbEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertModuleTask(moduleTask: ModuleTasksDbEntity)

}