package com.imprarce.android.network.repository.community

import com.imprarce.android.network.ResponseNetwork
import com.imprarce.android.network.model.community.Community
import java.time.LocalDateTime

interface CommunityNetworkRepository {
    suspend fun getCommunities(): ResponseNetwork<List<Community>>
    suspend fun createCommunity(title: String, description: String, currentTime: LocalDateTime): ResponseNetwork<Unit>
    suspend fun deleteCommunity(communityId: Int): ResponseNetwork<Unit>
}