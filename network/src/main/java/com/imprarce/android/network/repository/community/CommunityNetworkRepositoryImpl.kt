package com.imprarce.android.network.repository.community

import com.imprarce.android.network.ResponseNetwork
import com.imprarce.android.network.api.NetworkApi
import com.imprarce.android.network.model.comment.Comment
import com.imprarce.android.network.model.community.Community
import com.imprarce.android.network.model.task.Task
import com.imprarce.android.network.utils.SessionManager
import java.time.LocalDateTime
import javax.inject.Inject

class CommunityNetworkRepositoryImpl @Inject constructor(
    private val communityApi: NetworkApi,
    private val sessionManager: SessionManager
) : CommunityNetworkRepository {

    override suspend fun getCommunities(): ResponseNetwork<List<Community>> {
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

    override suspend fun createCommunity(title: String, description: String, currentTime: LocalDateTime): ResponseNetwork<Unit> {
        return try {
            val userId = sessionManager.getCurrentUserId()

            val community = Community(
                idCommunity = 0,
                idUser = userId!!,
                title = title,
                rating = 0,
                view = 0,
                createTime = currentTime,
                lastChange = currentTime,
                hasProblemResolve = false,
                description = description
            )

            val response = communityApi.createCommunity(community)
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
}