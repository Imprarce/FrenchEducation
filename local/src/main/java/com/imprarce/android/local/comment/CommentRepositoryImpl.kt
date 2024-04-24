package com.imprarce.android.local.comment

import com.imprarce.android.local.comment.room.CommentDao
import com.imprarce.android.local.comment.room.CommentDbEntity
import com.imprarce.android.local.comment.room.CommentRepository

class CommentRepositoryImpl(private val commentDao: CommentDao) : CommentRepository {
    override suspend fun insertComment(comment: CommentDbEntity) {
        commentDao.insertComment(comment)
    }

    override suspend fun getCommentById(commentId: Int): CommentDbEntity? {
        return commentDao.getCommentById(commentId)
    }
}