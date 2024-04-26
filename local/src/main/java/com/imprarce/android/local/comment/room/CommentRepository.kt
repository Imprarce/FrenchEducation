package com.imprarce.android.local.comment.room

import com.imprarce.android.local.ResponseRoom

interface CommentRepository {
    suspend fun insertComment(comment: CommentDbEntity): ResponseRoom<Unit>
    suspend fun getCommentById(commentId: Int): CommentDbEntity?

    suspend fun getAllComments(): ResponseRoom<List<CommentDbEntity>>

    suspend fun getCommentsByCommunityId(communityId: Int): ResponseRoom<List<CommentDbEntity>>
}