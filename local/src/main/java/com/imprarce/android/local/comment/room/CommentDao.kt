package com.imprarce.android.local.comment.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CommentDao {
    @Query("SELECT * FROM comment")
    suspend fun getAllTasks(): List<CommentDbEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComment(comment: CommentDbEntity)

    @Query("SELECT * FROM comment WHERE id_comment = :commentId")
    suspend fun getCommentById(commentId: Int): CommentDbEntity?

}