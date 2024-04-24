package com.imprarce.android.local.ielts.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface IeltsDao {
    @Insert
    suspend fun insertIelts(ielts: IeltsDbEntity)

    @Query("SELECT * FROM ielts")
    suspend fun getAllIelts(): List<IeltsDbEntity>
}