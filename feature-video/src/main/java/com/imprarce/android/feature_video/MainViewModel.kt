package com.imprarce.android.feature_video

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imprarce.android.feature_video.utils.Converter
import com.imprarce.android.local.ResponseRoom
import com.imprarce.android.local.user.room.UserDbEntity
import com.imprarce.android.local.user.room.UserRepository
import com.imprarce.android.local.video.VideoItem
import com.imprarce.android.local.video.room.VideoDbEntity
import com.imprarce.android.local.video.room.VideoRepository
import com.imprarce.android.network.ResponseNetwork
import com.imprarce.android.network.repository.user.UserRepositoryNetwork
import com.imprarce.android.network.repository.video.VideoNetworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: UserRepositoryNetwork,
    private val userRepository: UserRepository,
    private val videoNetworkRepository: VideoNetworkRepository,
    private val videoRepository: VideoRepository
) : ViewModel() {

    private val _userFromRoom = MutableLiveData<UserDbEntity>()
    val userFromRoom: LiveData<UserDbEntity> = _userFromRoom

    private val _videoList = MutableLiveData<List<VideoItem>>()
    val videoList: LiveData<List<VideoItem>> = _videoList


    init {
        getListVideoNetwork()
        Log.d("MainViewModel", "ViewModel created")
    }
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

    private fun getListVideoNetwork() {
        viewModelScope.launch {
            when (val response = videoNetworkRepository.getVideos()) {
                is ResponseNetwork.Success -> {
                    val videoNetworkList = response.result
                    _videoList.value = Converter.convertToVideoItemListNetwork(videoNetworkList)
                }
                is ResponseNetwork.Failure -> {
                    Log.e("MainViewModel", "Failed to load videos: ${response.exception}")
                }
                is ResponseNetwork.Loading -> {

                }
            }
        }
    }

    fun addNewVideo(title: String,  description: String,  videoFile: Uri) {

        viewModelScope.launch {
            when (val result = videoNetworkRepository.createVideo(0, 0, description, title, videoFile)) {
                is ResponseNetwork.Success -> {
                    getListVideoNetwork()
                }
                is ResponseNetwork.Failure -> {
                    Log.e("MainViewModel", "Failed to insert video: ${result.exception}")
                }
                else -> {}
            }
        }
    }

    private fun getListVideo() {
        viewModelScope.launch {
            when (val response = videoRepository.getAllVideos()) {
                is ResponseRoom.Success -> {
                    val videoDbList = response.result
                    _videoList.value = Converter.convertToVideoItemList(videoDbList)
                }
                is ResponseRoom.Failure -> {
                    Log.e("MainViewModel", "Failed to load videos: ${response.exception}")
                }
                is ResponseRoom.Loading -> {

                }
            }
        }
    }



    fun deleteAllVideos() {
        viewModelScope.launch(Dispatchers.IO) {
            videoRepository.deleteAllVideos()
            getListVideo()
        }
    }

//    fun loadVideo(videoId: Int, videoUri: Uri) {
//        viewModelScope.launch {
//            repository.loadVideo(videoId, videoUri)
//            val videoUrl = repository.getVideoUrl(videoId)
//            videoRepository.updateVideoFileByVideoId(videoId, videoUrl)
//            getListVideo()
//        }
//    }

    suspend fun getIdVideo(videoUri: Uri): Int {
        return suspendCoroutine { continuation ->
            viewModelScope.launch {
                when (val response =
                    videoRepository.getIdByVideoFile(videoFile = videoUri.toString())) {
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