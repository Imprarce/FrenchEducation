package com.imprarce.android.feature_video.utils

import com.imprarce.android.local.video.VideoItem
import com.imprarce.android.local.video.room.VideoDbEntity
import com.imprarce.android.network.model.video.Video

object Converter {

    fun convertToVideoItemListNetwork(videos: List<Video>): List<VideoItem> {
        return videos.map { videoNetwork ->
            VideoItem(
                videoId = videoNetwork.videoId,
                rating = videoNetwork.rating,
                view = videoNetwork.view,
                description = videoNetwork.description,
                title = videoNetwork.title,
                videoFile = videoNetwork.videoFile,
            )
        }
    }

    fun convertToVideoItemList(videoDbList: List<VideoDbEntity>): List<VideoItem> {
        return videoDbList.map { videoDbEntity ->
            VideoItem(
                videoId = videoDbEntity.videoId,
                rating = videoDbEntity.rating,
                view = videoDbEntity.view,
                description = videoDbEntity.description,
                title = videoDbEntity.title,
                videoFile = videoDbEntity.videoFile,
            )
        }
    }
}