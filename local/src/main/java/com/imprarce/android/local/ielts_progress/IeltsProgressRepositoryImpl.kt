package com.imprarce.android.local.ielts_progress

import com.imprarce.android.local.ielts_progress.room.IeltsProgressDao
import com.imprarce.android.local.ielts_progress.room.IeltsProgressDbEntity
import com.imprarce.android.local.ielts_progress.room.IeltsProgressRepository

class IeltsProgressRepositoryImpl(private val ieltsProgressDao: IeltsProgressDao) :
    IeltsProgressRepository {
    override suspend fun insertIeltsProgress(ieltsProgress: IeltsProgressDbEntity) {
        ieltsProgressDao.insertIeltsProgress(ieltsProgress)
    }

    override suspend fun getAllIeltsProgress(): List<IeltsProgressDbEntity> {
        return ieltsProgressDao.getAllIeltsProgress()
    }
}