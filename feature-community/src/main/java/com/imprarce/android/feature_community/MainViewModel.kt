package com.imprarce.android.feature_community

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.imprarce.android.local.AppDatabase
import com.imprarce.android.local.ResponseRoom
import com.imprarce.android.local.comment.CommentItem
import com.imprarce.android.local.comment.room.CommentDbEntity
import com.imprarce.android.local.comment.room.CommentRepository
import com.imprarce.android.local.community.CommunityItem
import com.imprarce.android.local.community.room.CommunityDbEntity
import com.imprarce.android.local.community.room.CommunityRepository
import com.imprarce.android.local.user.room.UserDbEntity
import com.imprarce.android.local.user.room.UserRepository
import com.imprarce.android.network.repository.FirebaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: FirebaseRepository,
    private val userRepository: UserRepository,
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

    private val currentUser: FirebaseUser?
        get() = repository.currentUser

    init {
        getUser()
        getCommunityList()
        Log.d("MainViewModel", "ViewModel created")
    }

    fun getUserId(): String {
        return currentUser?.uid ?: ""
    }

    fun getUser() = viewModelScope.launch {
        when (val response = repository.currentUser) {
            null -> {
            }
            else -> {
                val id_user = response.uid
                Log.d("MainViewModel", id_user)
                when (val userResponse = userRepository.getUserById(id_user)) {
                    is ResponseRoom.Success -> {
                        _userFromRoom.value = userResponse.result!!
                    }
                    is ResponseRoom.Failure -> {
                        Log.e("MainViewModel", "Failed to load user: ${userResponse.exception}")
                    }
                    is ResponseRoom.Loading -> {

                    }
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
                        _communityItem.value = convertToCommunityItem(communityDb)
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
                    _communityList.value = convertToCommunityItemList(communityDbList)
                }
                is ResponseRoom.Failure -> {
                    Log.e("MainViewModel", "Failed to load communities: ${response.exception}")
                }
                is ResponseRoom.Loading -> {

                }
            }
        }
    }

    private suspend fun getUserName(id_user: String): String{
        var userName = ""
        when (val userResponse = userRepository.getUserById(id_user)) {
            is ResponseRoom.Success -> {
                userName = userResponse.result!!.userName.toString()
            }
            is ResponseRoom.Failure -> {
                Log.e("MainViewModel", "Failed to load user: ${userResponse.exception}")
            }
            is ResponseRoom.Loading -> {

            }
        }
        return userName
    }

    private suspend fun getUserImage(id_user: String): String{
        var userImage = ""
        when (val userResponse = userRepository.getUserById(id_user)) {
            is ResponseRoom.Success -> {
                userImage = userResponse.result!!.imageUrl.toString()
            }
            is ResponseRoom.Failure -> {
                Log.e("MainViewModel", "Failed to load user: ${userResponse.exception}")
            }
            is ResponseRoom.Loading -> {

            }
        }
        return userImage
    }

    private suspend fun convertToCommunityItemList(communityDbList: List<CommunityDbEntity>): List<CommunityItem> {
        return communityDbList.map { communityDbEntity ->
            CommunityItem(
                id_community = communityDbEntity.id_community,
                id_user = communityDbEntity.id_user,
                user_name = getUserName(communityDbEntity.id_user),
                user_image = getUserImage(communityDbEntity.id_user),
                title = communityDbEntity.title,
                rating = communityDbEntity.rating,
                view = communityDbEntity.view,
                create_time = communityDbEntity.create_time,
                last_change = communityDbEntity.last_change,
                has_problem_resolve = communityDbEntity.has_problem_resolve,
                description = communityDbEntity.description,
                arrowRatingImageResource = R.drawable.arrow_up_rating
            )
        }
    }

    private suspend fun convertToCommunityItem(communityDb: CommunityDbEntity): CommunityItem {
        return CommunityItem(
            id_community = communityDb.id_community,
            id_user = communityDb.id_user,
            user_name = getUserName(communityDb.id_user),
            user_image = getUserImage(communityDb.id_user),
            title = communityDb.title,
            rating = communityDb.rating,
            view = communityDb.view,
            create_time = communityDb.create_time,
            last_change = communityDb.last_change,
            has_problem_resolve = communityDb.has_problem_resolve,
            description = communityDb.description,
            arrowRatingImageResource = R.drawable.arrow_up_rating
        )
    }

    fun addNewMessage(idUser: String, title: String, description: String, currentTime: String){
        val community = CommunityDbEntity(
            id_user = idUser,
            title = title,
            rating = 0,
            view = 0,
            create_time = currentTime,
            last_change = currentTime,
            has_problem_resolve = false,
            description = description
        )
        viewModelScope.launch {
            communityRepository.insertCommunity(community)
            getCommunityList()
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

    fun insertComment(idUser: String, communityId: Int, message: String, rating: Int) {
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
                    _commentsList.value = convertToCommentsItemList(commentsDbList)
                }
                is ResponseRoom.Failure -> {
                    Log.e("MainViewModel", "Failed to load communities: ${response.exception}")
                }
                is ResponseRoom.Loading -> {

                }
            }
        }
    }

    private suspend fun convertToCommentsItemList(commentsDbList: List<CommentDbEntity>): List<CommentItem> {
        return commentsDbList.map { commentsDbEntity ->
            CommentItem(
                userId = commentsDbEntity.userId,
                userImage = getUserImage(commentsDbEntity.userId),
                userName = getUserName(commentsDbEntity.userId),
                message = commentsDbEntity.message
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("MainViewModel", "ViewModel destroyed")
    }
}