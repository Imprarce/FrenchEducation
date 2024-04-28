package com.imprarce.android.local.video.room

import androidx.room.*

@Dao
interface VideoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVideo(video: VideoDbEntity)

    @Delete
    suspend fun deleteVideo(video: VideoDbEntity)

    @Query("DELETE FROM video")
    suspend fun deleteAllVideos()

    @Query("SELECT * FROM video")
    suspend fun getAllVideos(): List<VideoDbEntity>

    @Query("UPDATE video SET video_file = :newVideoFile WHERE id_video = :videoId")
    suspend fun updateVideoFileByVideoId(videoId: Int, newVideoFile: String)

    @Query("SELECT id_video FROM video WHERE video_file = :videoFile LIMIT 1")
    suspend fun getIdByVideoFile(videoFile: String): Int
}