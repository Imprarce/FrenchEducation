package com.imprarce.android.feature_community

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imprarce.android.feature_community.utils.Converter
import com.imprarce.android.local.ResponseRoom
import com.imprarce.android.local.comment.CommentItem
import com.imprarce.android.local.comment.room.CommentDbEntity
import com.imprarce.android.local.comment.room.CommentRepository
import com.imprarce.android.local.community.CommunityItem
import com.imprarce.android.local.community.room.CommunityDbEntity
import com.imprarce.android.local.community.room.CommunityRepository
import com.imprarce.android.local.user.room.UserDbEntity
import com.imprarce.android.local.user.room.UserRepository
import com.imprarce.android.network.repository.user.UserRepositoryNetwork
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val userNetworkRepository: UserRepositoryNetwork,
    private val communityRepository: CommunityRepository,
    private val commentRepository: CommentRepository
) : ViewModel() {

    private val _userFromRoom = MutableLiveData<UserDbEntity>()
    val userFromRoom: LiveData<UserDbEntity> = _userFromRoom

    private val _communityList = MutableLiveData<List<CommunityItem>>()
    val communityList: LiveData<List<CommunityItem>> = _communityList

    private val _communityItem = MutableLiveData<CommunityItem>()
    val communityItem: LiveData<CommunityItem> = _communityItem

    private val _commentsList = MutableLiveData<List<CommentItem>>()
    val commentsList: LiveData<List<CommentItem>> = _commentsList

    private fun getCommunityListNetwork() {
        viewModelScope.launch {
            when (val response = communityRepository.getAllCommunities()) {
                is ResponseRoom.Success -> {
                    val communityDbList = response.result
                    _communityList.value = Converter(userRepository, userNetworkRepository).convertToCommunityItemList(communityDbList)
                }
                is ResponseRoom.Failure -> {
                    Log.e("MainViewModel", "Failed to load communities: ${response.exception}")
                }
                is ResponseRoom.Loading -> {

                }
            }
        }
    }

    fun getCommunityItem(idCommunityItem: Int){
        viewModelScope.launch {
            when(val response = communityRepository.getCommunityById(idCommunityItem)){
                is ResponseRoom.Success -> {
                    val communityDb = response.result
                    if(communityDb != null){
                        _communityItem.value = Converter(userRepository, userNetworkRepository).convertToCommunityItem(communityDb)
                    }
                }
                is ResponseRoom.Failure -> {
                    Log.e("MainViewModel", "Failed to load communities: ${response.exception}")
                }
                else -> {}
            }
        }
    }

    private fun getCommunityList() {
        viewModelScope.launch {
            when (val response = communityRepository.getAllCommunities()) {
                is ResponseRoom.Success -> {
                    val communityDbList = response.result
                    _communityList.value = Converter(userRepository, userNetworkRepository).convertToCommunityItemList(communityDbList)
                }
                is ResponseRoom.Failure -> {
                    Log.e("MainViewModel", "Failed to load communities: ${response.exception}")
                }
                is ResponseRoom.Loading -> {

                }
            }
        }
    }



    fun addNewMessage(idUser: Int, title: String, description: String, currentTime: LocalDateTime){
        viewModelScope.launch {

        }
    }

    fun deleteCommunity(communityItem: CommunityItem){
        val communityDbEntity = CommunityDbEntity(
            id_community = communityItem.id_community,
            id_user = communityItem.id_user,
            title = communityItem.title,
            rating = communityItem.rating,
            view = communityItem.view,
            create_time = communityItem.create_time,
            last_change = communityItem.last_change,
            has_problem_resolve = communityItem.has_problem_resolve,
            description = communityItem.description
        )

        viewModelScope.launch {
            when (val response = communityRepository.deleteCommunity(communityDbEntity)) {
                is ResponseRoom.Success -> {
                    getCommunityList()
                }
                is ResponseRoom.Failure -> {
                    Log.e("MainViewModel", "Failed to load communities: ${response.exception}")
                }
                is ResponseRoom.Loading -> {

                }
            }
        }
    }

    fun insertComment(idUser: Int, communityId: Int, message: String, rating: Int) {
        val comment = CommentDbEntity(
            userId = idUser,
            communityId = communityId,
            message = message,
            rating = rating
        )
        viewModelScope.launch {
            when (val response = commentRepository.insertComment(comment)) {
                is ResponseRoom.Success -> {
                    getCommentsList(communityId)
                }
                is ResponseRoom.Failure -> {
                    Log.e("MainViewModel", "Failed to insert comment: ${response.exception}")
                }
                else -> {}
            }
        }
    }

    fun getCommentsList(communityId: Int){
        viewModelScope.launch {
            when (val response = commentRepository.getCommentsByCommunityId(communityId)) {
                is ResponseRoom.Success -> {
                    val commentsDbList = response.result
                    _commentsList.value = Converter(userRepository, userNetworkRepository).convertToCommentsItemList(commentsDbList)
                }
                is ResponseRoom.Failure -> {
                    Log.e("MainViewModel", "Failed to load communities: ${response.exception}")
                }
                is ResponseRoom.Loading -> {

                }
            }
        }
    }



    override fun onCleared() {
        super.onCleared()
        Log.d("MainViewModel", "ViewModel destroyed")
    }
}