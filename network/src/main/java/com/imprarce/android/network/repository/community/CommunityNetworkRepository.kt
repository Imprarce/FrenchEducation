package com.imprarce.android.network.repository.community

import com.imprarce.android.network.ResponseNetwork
import com.imprarce.android.network.model.community.CommunityResponse
import com.imprarce.android.network.model.community.CommunitySend

interface CommunityNetworkRepository {
    suspend fun getCommunities(): ResponseNetwork<List<CommunityResponse>>
    suspend fun createCommunity(title: String, userImage: String, userName: String, description: String): ResponseNetwork<Unit>
    suspend fun deleteCommunity(communityId: Int): ResponseNetwork<Unit>

    suspend fun getCommunityById(communityId: Int): ResponseNetwork<CommunityResponse?>
}