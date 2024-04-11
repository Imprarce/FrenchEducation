package com.imprarce.android.frencheducation.data.db.module_progress.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ModuleDao {
    @Query("SELECT * FROM module")
    suspend fun getAllModules(): List<ModuleDbEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertModule(module: ModuleDbEntity)

}