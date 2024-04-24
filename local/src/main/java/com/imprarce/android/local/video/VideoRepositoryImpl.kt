package com.imprarce.android.local.video

import com.imprarce.android.local.video.room.VideoDao
import com.imprarce.android.local.video.room.VideoDbEntity
import com.imprarce.android.local.video.room.VideoRepository

class VideoRepositoryImpl(private val dao: VideoDao) : VideoRepository {
    override suspend fun insertVideo(video: VideoDbEntity) {
        dao.insertVideo(video)
    }

    override suspend fun deleteVideo(video: VideoDbEntity) {
        dao.deleteVideo(video)
    }

    override suspend fun getVideosForUser(userId: String): List<VideoDbEntity> {
        return dao.getVideosForUser(userId)
    }
}