package com.imprarce.android.local.community.room

import androidx.room.*

@Dao
interface CommunityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCommunity(community: CommunityDbEntity)

    @Query("SELECT * FROM community WHERE id_community = :id")
    suspend fun getCommunityById(id: Int): CommunityDbEntity?

    @Query("SELECT * FROM community")
    suspend fun getAllCommunities(): List<CommunityDbEntity>

    @Delete
    suspend fun deleteCommunity(community: CommunityDbEntity)
}