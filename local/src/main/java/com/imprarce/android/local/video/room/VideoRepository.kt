package com.imprarce.android.local.video.room

interface VideoRepository {
    suspend fun insertVideo(video: VideoDbEntity)
    suspend fun deleteVideo(video: VideoDbEntity)
    suspend fun getVideosForUser(userId: String): List<VideoDbEntity>
}