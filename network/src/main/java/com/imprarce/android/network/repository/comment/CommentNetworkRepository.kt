package com.imprarce.android.network.repository.comment

import com.imprarce.android.network.ResponseNetwork
import com.imprarce.android.network.model.comment.Comment
import com.imprarce.android.network.model.task.Task

interface CommentNetworkRepository {
    suspend fun getComments(communityId: Int): ResponseNetwork<List<Comment>>
    suspend fun createComment(communityId: Int, userImage: String, userName: String, message: String, rating: Int): ResponseNetwork<Unit>
    suspend fun deleteComment(commentId: Int): ResponseNetwork<Unit>
}
