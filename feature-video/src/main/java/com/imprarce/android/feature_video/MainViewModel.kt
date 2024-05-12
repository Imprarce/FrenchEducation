package com.imprarce.android.feature_video

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imprarce.android.local.ResponseRoom
import com.imprarce.android.local.user.room.UserDbEntity
import com.imprarce.android.local.user.room.UserRepository
import com.imprarce.android.local.video.VideoItem
import com.imprarce.android.local.video.room.VideoDbEntity
import com.imprarce.android.local.video.room.VideoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: com.imprarce.android.network.repository.user.UserRepositoryNetwork,
    private val userRepository: UserRepository,
    private val videoRepository: VideoRepository
) : ViewModel() {

    private val _userFromRoom = MutableLiveData<UserDbEntity>()
    val userFromRoom: LiveData<UserDbEntity> = _userFromRoom

    private val _videoList = MutableLiveData<List<VideoItem>>()
    val videoList: LiveData<List<VideoItem>> = _videoList

//    val currentUser: FirebaseUser?
//        get() = repository.currentUser
//
//    init {
//        getUser()
//        getListVideo()
//        Log.d("MainViewModel", "ViewModel created")
//    }
//
//    fun getUserId(): String {
//        return currentUser?.uid ?: ""
//    }
//
//    private fun getUser() = viewModelScope.launch {
//        when (val response = repository.currentUser) {
//            null -> {
//            }
//            else -> {
//                val id_user = response.uid
//                Log.d("MainViewModel", id_user)
//                when (val userResponse = userRepository.getUserById(id_user)) {
//                    is ResponseRoom.Success -> {
//                        _userFromRoom.value = userResponse.result!!
//                    }
//                    is ResponseRoom.Failure -> {
//                        Log.e("MainViewModel", "Failed to load user: ${userResponse.exception}")
//                    }
//                    is ResponseRoom.Loading -> {
//
//                    }
//                }
//            }
//        }
//    }

    private fun getListVideo(){
        viewModelScope.launch {
            viewModelScope.launch {
                when (val response = videoRepository.getAllVideos()) {
                    is ResponseRoom.Success -> {
                        val videoDbList = response.result
                        _videoList.value = convertToVideoItemList(videoDbList)
                    }
                    is ResponseRoom.Failure -> {
                        Log.e("MainViewModel", "Failed to load videos: ${response.exception}")
                    }
                    is ResponseRoom.Loading -> {

                    }
                }
            }
        }
    }

    fun addNewVideo(idUser: String, title: String, description: String, videoFile: String, imageUrl: String?){
        val video = VideoDbEntity(
            userId = idUser,
            rating = 0,
            view = 0,
            description = description,
            title = title,
            videoFile = videoFile,
            imageUrl = imageUrl
        )
        viewModelScope.launch {
            when(val result = videoRepository.insertVideo(video)){
                is ResponseRoom.Success -> {
                    getListVideo()
                }
                is ResponseRoom.Failure -> {
                    Log.e("MainViewModel", "Failed to insert video: ${result.exception}")
                }
                else -> {}
            }
        }
    }

    fun deleteAllVideos() {
        viewModelScope.launch(Dispatchers.IO) {
            videoRepository.deleteAllVideos()
            getListVideo()
        }
    }

    private suspend fun convertToVideoItemList(videoDbList: List<VideoDbEntity>): List<VideoItem> {
        return videoDbList.map { videoDbEntity ->
            VideoItem(
                videoId = videoDbEntity.videoId,
                rating = videoDbEntity.rating,
                view = videoDbEntity.view,
                description = videoDbEntity.description,
                title = videoDbEntity.title,
                videoFile = videoDbEntity.videoFile,
                imageUrl = null
            )
        }
    }

    fun loadVideo(videoId: Int, videoUri: Uri){
        viewModelScope.launch {
            repository.loadVideo(videoId, videoUri)
            val videoUrl = repository.getVideoUrl(videoId)
            videoRepository.updateVideoFileByVideoId(videoId, videoUrl)
            getListVideo()
        }
    }

    suspend fun getIdVideo(videoUri: Uri): Int{
        return suspendCoroutine { continuation ->
            viewModelScope.launch {
                when (val response = videoRepository.getIdByVideoFile(videoFile = videoUri.toString())) {
                    is ResponseRoom.Success -> continuation.resume(response.result)
                    is ResponseRoom.Failure -> {
                        Log.e("getIdVideo", "${response.exception}")
                        continuation.resume(0)
                    }
                    else -> {
                    }
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("MainViewModel", "ViewModel destroyed")
    }
}