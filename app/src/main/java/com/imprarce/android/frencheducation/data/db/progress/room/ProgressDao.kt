package com.imprarce.android.frencheducation.data.db.progress.room

import androidx.room.Dao
import androidx.room.Query

@Dao
interface ProgressDao {
    @Query("SELECT * FROM progress")
    suspend fun getAllProgress(): List<ProgressDbEntity>

    @Query("SELECT * FROM progress WHERE id_user = :userId")
    suspend fun getProgressByUserId(userId: Int): List<ProgressDbEntity>

}
