package com.imprarce.android.local.ielts

import com.imprarce.android.local.ielts.room.IeltsDao
import com.imprarce.android.local.ielts.room.IeltsDbEntity
import com.imprarce.android.local.ielts.room.IeltsRepository

class IeltsRepositoryImpl(private val ieltsDao: IeltsDao) : IeltsRepository {
    override suspend fun insertIelts(ielts: IeltsDbEntity) {
        ieltsDao.insertIelts(ielts)
    }

    override suspend fun getAllIelts(): List<IeltsDbEntity> {
        return ieltsDao.getAllIelts()
    }
}