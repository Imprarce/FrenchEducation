package com.imprarce.android.local.video.room

import androidx.room.*

@Dao
interface VideoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVideo(video: VideoDbEntity)

    @Delete
    suspend fun deleteVideo(video: VideoDbEntity)

    @Query("SELECT * FROM video WHERE id_user = :userId")
    suspend fun getVideosForUser(userId: String): List<VideoDbEntity>
}