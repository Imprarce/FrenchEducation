package com.imprarce.android.local.community.room

import com.imprarce.android.local.ResponseRoom

interface CommunityRepository {

    suspend fun insertCommunity(community: CommunityDbEntity)

    suspend fun getCommunityById(id: Int): ResponseRoom<CommunityDbEntity?>

    suspend fun getAllCommunities(): ResponseRoom<List<CommunityDbEntity>>

    suspend fun deleteCommunity(community: CommunityDbEntity): ResponseRoom<Unit>
}