package com.imprarce.android.local.ielts_progress.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface IeltsProgressDao {
    @Insert
    suspend fun insertIeltsProgress(ieltsProgress: IeltsProgressDbEntity)

    @Query("SELECT * FROM ielts_progress")
    suspend fun getAllIeltsProgress(): List<IeltsProgressDbEntity>
}