package com.imprarce.android.local.video

import com.imprarce.android.local.ResponseRoom
import com.imprarce.android.local.video.room.VideoDao
import com.imprarce.android.local.video.room.VideoDbEntity
import com.imprarce.android.local.video.room.VideoRepository

class VideoRepositoryImpl(private val dao: VideoDao) : VideoRepository {
    override suspend fun insertVideo(video: VideoDbEntity): ResponseRoom<Unit> {
        return try {
            val response = dao.insertVideo(video)
            ResponseRoom.Success(response)
        } catch (e: Exception){
            ResponseRoom.Failure(e)
        }
    }

    override suspend fun deleteVideo(video: VideoDbEntity) {
        dao.deleteVideo(video)
    }

    override suspend fun getAllVideos(): ResponseRoom<List<VideoDbEntity>> {
        return try {
            val response = dao.getAllVideos()
            ResponseRoom.Success(response)
        } catch (e: Exception){
            ResponseRoom.Failure(e)
        }
    }

    override suspend fun deleteAllVideos(): ResponseRoom<Unit> {
        return try {
            val response = dao.deleteAllVideos()
            ResponseRoom.Success(response)
        } catch (e: Exception){
            ResponseRoom.Failure(e)
        }
    }

    override suspend fun updateVideoFileByVideoId(
        videoId: Int,
        newVideoFile: String
    ): ResponseRoom<Unit> {
        return try {
            val response = dao.updateVideoFileByVideoId(videoId, newVideoFile)
            ResponseRoom.Success(response)
        } catch (e: Exception){
            ResponseRoom.Failure(e)
        }
    }

    override suspend fun getIdByVideoFile(videoFile: String): ResponseRoom<Int> {
        return try {
            val response = dao.getIdByVideoFile(videoFile)
            ResponseRoom.Success(response)
        } catch (e: Exception){
            ResponseRoom.Failure(e)
        }
    }

}