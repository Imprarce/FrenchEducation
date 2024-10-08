package com.imprarce.android.network.repository.community

import com.imprarce.android.network.ResponseNetwork
import com.imprarce.android.network.api.NetworkApi
import com.imprarce.android.network.model.community.CommunityResponse
import com.imprarce.android.network.model.community.CommunitySend
import com.imprarce.android.network.utils.SessionManager
import javax.inject.Inject

class CommunityNetworkRepositoryImpl @Inject constructor(
    private val communityApi: NetworkApi,
    private val sessionManager: SessionManager
) : CommunityNetworkRepository {

    override suspend fun getCommunities(): ResponseNetwork<List<CommunityResponse>> {
        return try {
            val response = communityApi.getCommunities()
            if (response.isSuccessful) {
                val tasks = response.body() ?: emptyList()
                ResponseNetwork.Success(tasks)
            } else {
                ResponseNetwork.Failure(response.message())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            ResponseNetwork.Failure(e.message ?: "Возникла какая-то ошибка")
        }
    }

    override suspend fun createCommunity(title: String, userImage: String, userName: String, description: String): ResponseNetwork<Unit> {
        return try {
            val userId = sessionManager.getCurrentUserId()

            val communitySend = CommunitySend(
                idCommunity = 0,
                idUser = userId!!,
                userImage = userImage,
                userName = userName,
                title = title,
                rating = 0,
                view = 0,
                hasProblemResolve = false,
                description = description
            )

            val response = communityApi.createCommunity(communitySend)
            if (response.success) {
                ResponseNetwork.Success(Unit)
            } else {
                ResponseNetwork.Failure("Что-то пошло не так")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            ResponseNetwork.Failure(e.message ?: "Возникла какая-то ошибка")
        }
    }

    override suspend fun deleteCommunity(communityId: Int): ResponseNetwork<Unit> {
        return try {
            val response = communityApi.deleteCommunity(communityId)
            if (response.success) {
                ResponseNetwork.Success(Unit)
            } else {
                ResponseNetwork.Failure("Произошла ошибка")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            ResponseNetwork.Failure(e.message ?: "Возникла какая-то ошибка")
        }
    }

    override suspend fun getCommunityById(communityId: Int): ResponseNetwork<CommunityResponse?>{
        return try {
            val response = communityApi.getCommunityById(communityId)
            if (response.isSuccessful) {
                ResponseNetwork.Success(response.body())
            } else {
                ResponseNetwork.Failure(response.message())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            ResponseNetwork.Failure(e.message ?: "Возникла какая-то ошибка")
        }
    }
}