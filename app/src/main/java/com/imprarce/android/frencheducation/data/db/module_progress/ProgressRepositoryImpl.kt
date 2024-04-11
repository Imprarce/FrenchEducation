package com.imprarce.android.frencheducation.data.db.module_progress

import com.imprarce.android.frencheducation.data.db.ResponseRoom
import com.imprarce.android.frencheducation.data.db.module_progress.room.ModuleProgressDao
import com.imprarce.android.frencheducation.data.db.module_progress.room.ModuleProgressDbEntity
import com.imprarce.android.frencheducation.data.db.module_progress.room.ModuleProgressRepository

class ProgressRepositoryImpl(private val moduleProgressDao: ModuleProgressDao) : ModuleProgressRepository {
    override suspend fun getAllModuleProgress(): ResponseRoom<List<ModuleProgressDbEntity>> {
        return try{
            val response = moduleProgressDao.getAllModuleProgress()
            ResponseRoom.Success(response)
        } catch (e: Exception){
            ResponseRoom.Failure(e)
        }
    }

    override suspend fun getModuleProgressByUserId(userId: String): ResponseRoom<List<ModuleProgressDbEntity>> {
        return try{
            val response = moduleProgressDao.getModuleProgressByUserId(userId)
            if(response != null){
                ResponseRoom.Success(response)
            } else {
                ResponseRoom.Failure(response)
            }
        } catch (e: Exception){
            ResponseRoom.Failure(e)
        }
    }

    override suspend fun updateModuleProgress(
        userId: String,
        moduleId: Int
    ): ResponseRoom<Unit> {
        return try{
            val response = moduleProgressDao.updateModuleProgress(userId, moduleId)
            ResponseRoom.Success(response)
        } catch (e: Exception){
            ResponseRoom.Failure(e)
        }
    }
}