package com.imprarce.android.network.repository.comment

import com.imprarce.android.network.ResponseNetwork
import com.imprarce.android.network.api.NetworkApi
import com.imprarce.android.network.model.comment.Comment
import com.imprarce.android.network.model.task.Task
import com.imprarce.android.network.model.task_completed.TaskCompleted
import com.imprarce.android.network.utils.SessionManager
import javax.inject.Inject

class CommentNetworkRepositoryImpl @Inject constructor(
    private val commentApi: NetworkApi,
    private val sessionManager: SessionManager
) : CommentNetworkRepository {

    override suspend fun getComments(communityId: Int): ResponseNetwork<List<Comment>> {
        return try {
            val response = commentApi.getComments(communityId)
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

    override suspend fun createComment(communityId: Int, message: String, rating: Int): ResponseNetwork<Unit> {
        return try {
            val userId = sessionManager.getCurrentUserId()

            val comment = Comment(
                idComment = 0,
                idUser = userId!!,
                idCommunity = communityId,
                message = message,
                rating = rating
            )

            val response = commentApi.createComment(comment)
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

    override suspend fun deleteComment(commentId: Int): ResponseNetwork<Unit> {
        return try {
            val response = commentApi.deleteComment(commentId)
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