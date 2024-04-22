package com.imprarce.android.local.module_progress.room

import androidx.room.Dao
import androidx.room.Query

@Dao
interface ModuleProgressDao {
    @Query("SELECT * FROM module_progress")
    suspend fun getAllModuleProgress(): List<ModuleProgressDbEntity>

    @Query("SELECT * FROM module_progress WHERE id_user = :userId")
    suspend fun getModuleProgressByUserId(userId: String): List<ModuleProgressDbEntity>

    @Query("""
       INSERT INTO module_progress (id_user, id_module, module_progress)
    SELECT :userId, mt.id_module, 
           (CASE WHEN COUNT(tc.id_task) > 0 THEN 
               (CAST(COUNT(tc.id_task) AS INTEGER) * 100 / COUNT(mt.id_task))
            ELSE 0
            END) as module_progress
    FROM module_tasks mt
    LEFT JOIN task_completed tc ON mt.id_task = tc.id_task AND tc.id_user = :userId
    WHERE mt.id_module = :moduleId
    GROUP BY mt.id_module
    ON CONFLICT(id_user, id_module) DO UPDATE SET module_progress = excluded.module_progress
    """)
    suspend fun updateModuleProgress(userId: String, moduleId: Int)
}
