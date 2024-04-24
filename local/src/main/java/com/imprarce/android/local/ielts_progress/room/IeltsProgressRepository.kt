package com.imprarce.android.local.ielts_progress.room

interface IeltsProgressRepository {
    suspend fun insertIeltsProgress(ieltsProgress: IeltsProgressDbEntity)
    suspend fun getAllIeltsProgress(): List<IeltsProgressDbEntity>
}