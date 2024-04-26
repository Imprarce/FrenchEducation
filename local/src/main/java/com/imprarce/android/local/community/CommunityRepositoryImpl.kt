package com.imprarce.android.local.community

import com.imprarce.android.local.ResponseRoom
import com.imprarce.android.local.community.room.CommunityDao
import com.imprarce.android.local.community.room.CommunityDbEntity
import com.imprarce.android.local.community.room.CommunityRepository
import com.imprarce.android.local.dictionary.room.DictionaryDao
import com.imprarce.android.local.dictionary.room.DictionaryDbEntity
import com.imprarce.android.local.dictionary.room.DictionaryRepository

class CommunityRepositoryImpl(private val communityDao: CommunityDao) : CommunityRepository {

    override suspend fun insertCommunity(community: CommunityDbEntity) {
        communityDao.insertCommunity(community)
    }

    override suspend fun getCommunityById(id: Int): ResponseRoom<CommunityDbEntity?> {
        return try{
            val response = communityDao.getCommunityById(id)
            ResponseRoom.Success(response)
        } catch (e: Exception){
            ResponseRoom.Failure(e)
        }
    }

    override suspend fun getAllCommunities(): ResponseRoom<List<CommunityDbEntity>> {
        val response = communityDao.getAllCommunities()
        return ResponseRoom.Success(response)
    }

    override suspend fun deleteCommunity(community: CommunityDbEntity): ResponseRoom<Unit> {
        return try{
            val response = communityDao.deleteCommunity(community)
            ResponseRoom.Success(response)
        } catch (e: Exception){
            ResponseRoom.Failure(e)
        }
    }
}