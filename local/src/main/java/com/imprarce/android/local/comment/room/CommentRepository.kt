package com.imprarce.android.local.comment.room

interface CommentRepository {
    suspend fun insertComment(comment: CommentDbEntity)
    suspend fun getCommentById(commentId: Int): CommentDbEntity?
}