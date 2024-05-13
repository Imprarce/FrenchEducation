package com.imprarce.android.network.repository.video

import android.net.Uri
import com.imprarce.android.network.ResponseNetwork
import com.imprarce.android.network.SimpleResponse
import com.imprarce.android.network.api.NetworkApi
import com.imprarce.android.network.model.community.CommunityResponse
import com.imprarce.android.network.model.community.CommunitySend
import com.imprarce.android.network.model.video.Video
import com.imprarce.android.network.utils.SessionManager
import com.imprarce.android.network.utils.getPathFromUri
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject

class VideoNetworkRepositoryImpl @Inject constructor(
    private val videoApi: NetworkApi,
    private val sessionManager: SessionManager
) : VideoNetworkRepository {

    override suspend fun getVideos(): ResponseNetwork<List<Video>> {
        return try {
            val response = videoApi.getVideos()
            if (response.isSuccessful) {
                val tasks = response.body() ?: emptyList()
                ResponseNetwork.Success(tasks)
            } else {
                ResponseNetwork.Failure(response.message())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            ResponseNetwork.Failure(e.message ?: "Возникла какая-то ошибка")
        }
    }

    override suspend fun createVideo(rating: Int, view: Int, description: String, title: String, videoFileUri: Uri) : ResponseNetwork<Unit> {
        return try {
            val userId = sessionManager.getCurrentUserId()
            val context = sessionManager.context

            val videoPath = getPathFromUri(context, videoFileUri)

            if (videoPath == null) {
                println("Failed to get file path from Uri")
                return ResponseNetwork.Failure("Произошла ошибка")
            }

            val videoFile = File(videoPath)
            if (!videoFile.exists()) {
                println("File does not exist")
                return ResponseNetwork.Failure("Произошла ошибка")
            }

            val fileName = userId!!.toString() + ".mp4"
            val videoRequestBody = RequestBody.create("video/*".toMediaTypeOrNull(), videoFile)
            val videoPart = MultipartBody.Part.createFormData("videos", fileName, videoRequestBody)


            val response = videoApi.createVideo(0, userId!!, rating, view, description, title, videoPart)
            if (response.success) {
                ResponseNetwork.Success(Unit)
            } else {
                ResponseNetwork.Failure("Что-то пошло не так")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            ResponseNetwork.Failure(e.message ?: "Возникла какая-то ошибка")
        }
    }

    override suspend fun deleteVideo(videoId: Int): ResponseNetwork<Unit> {
        return try {
            val response = videoApi.deleteVideo(videoId)
            if (response.success) {
                ResponseNetwork.Success(Unit)
            } else {
                ResponseNetwork.Failure("Произошла ошибка")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            ResponseNetwork.Failure(e.message ?: "Возникла какая-то ошибка")
        }
    }

    override suspend fun getVideoById(videoId: Int): ResponseNetwork<Video?>{
        return try {
            val response = videoApi.getVideoById(videoId)
            if (response.isSuccessful) {
                ResponseNetwork.Success(response.body())
            } else {
                ResponseNetwork.Failure(response.message())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            ResponseNetwork.Failure(e.message ?: "Возникла какая-то ошибка")
        }
    }
}