package com.imprarce.android.local.video.room

import com.imprarce.android.local.ResponseRoom

interface VideoRepository {
    suspend fun insertVideo(video: VideoDbEntity): ResponseRoom<Unit>
    suspend fun deleteVideo(video: VideoDbEntity)
    suspend fun getAllVideos(): ResponseRoom<List<VideoDbEntity>>

    suspend fun deleteAllVideos(): ResponseRoom<Unit>

    suspend fun updateVideoFileByVideoId(videoId: Int, newVideoFile: String): ResponseRoom<Unit>

    suspend fun getIdByVideoFile(videoFile: String): ResponseRoom<Int>
}