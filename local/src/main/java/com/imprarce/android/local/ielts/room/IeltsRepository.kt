package com.imprarce.android.local.ielts.room

interface IeltsRepository {
    suspend fun insertIelts(ielts: IeltsDbEntity)
    suspend fun getAllIelts(): List<IeltsDbEntity>
}