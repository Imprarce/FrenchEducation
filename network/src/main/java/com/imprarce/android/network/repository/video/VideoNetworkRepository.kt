package com.imprarce.android.network.repository.video

import android.net.Uri
import com.imprarce.android.network.ResponseNetwork
import com.imprarce.android.network.model.community.CommunityResponse
import com.imprarce.android.network.model.video.Video

interface VideoNetworkRepository {
    suspend fun getVideos(): ResponseNetwork<List<Video>>
    suspend fun createVideo(rating: Int, view: Int, description: String, title: String, videoFile: Uri) : ResponseNetwork<Unit>
    suspend fun deleteVideo(videoId: Int): ResponseNetwork<Unit>
    suspend fun getVideoById(videoId: Int): ResponseNetwork<Video?>
}