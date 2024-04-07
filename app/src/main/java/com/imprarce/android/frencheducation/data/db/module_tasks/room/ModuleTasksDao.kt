package com.imprarce.android.frencheducation.data.db.progress.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ModuleTasksDao {
    @Query("SELECT * FROM module_tasks")
    suspend fun getAllModuleTasks(): List<ModuleTasksDbEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertModuleTask(moduleTask: ModuleTasksDbEntity)

}