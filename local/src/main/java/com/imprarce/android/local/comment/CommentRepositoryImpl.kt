package com.imprarce.android.local.comment

import com.imprarce.android.local.ResponseRoom
import com.imprarce.android.local.comment.room.CommentDao
import com.imprarce.android.local.comment.room.CommentDbEntity
import com.imprarce.android.local.comment.room.CommentRepository

class CommentRepositoryImpl(private val commentDao: CommentDao) : CommentRepository {
    override suspend fun insertComment(comment: CommentDbEntity): ResponseRoom<Unit> {
        return try{
            val response = commentDao.insertComment(comment)
            ResponseRoom.Success(response)
        } catch (e: Exception){
            ResponseRoom.Failure(e)
        }
    }

    override suspend fun getCommentById(commentId: Int): CommentDbEntity? {
        return commentDao.getCommentById(commentId)
    }

    override suspend fun getAllComments(): ResponseRoom<List<CommentDbEntity>> {
        return try{
            val response = commentDao.getAllComments()
            ResponseRoom.Success(response)
        } catch (e: Exception){
            ResponseRoom.Failure(e)
        }
    }

    override suspend fun getCommentsByCommunityId(communityId: Int): ResponseRoom<List<CommentDbEntity>> {
        return try{
            val response = commentDao.getCommentsByCommunityId(communityId)
            ResponseRoom.Success(response)
        } catch (e: Exception){
            ResponseRoom.Failure(e)
        }
    }
}