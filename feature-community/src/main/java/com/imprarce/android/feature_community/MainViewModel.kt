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
    private val communityRepository: CommunityRepository
) : ViewModel() {

    private val _userFromRoom = MutableLiveData<UserDbEntity>()
    val userFromRoom: LiveData<UserDbEntity> = _userFromRoom

    private val _communityList = MutableLiveData<List<CommunityItem>>()
    val communityList: LiveData<List<CommunityItem>> = _communityList

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

    fun getCommunityList() {
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

    private fun convertToCommunityItemList(communityDbList: List<CommunityDbEntity>): List<CommunityItem> {
        return communityDbList.map { communityDbEntity ->
            CommunityItem(
                id_community = communityDbEntity.id_community,
                id_user = communityDbEntity.id_user,
                title = communityDbEntity.title,
                rating = communityDbEntity.rating,
                view = communityDbEntity.view,
                create_time = communityDbEntity.create_time,
                last_change = communityDbEntity.last_change,
                has_problem_resolve = communityDbEntity.has_problem_resolve,
                description = communityDbEntity.description,
                imageResource = R.drawable.image_plug,
                arrowRatingImageResource = R.drawable.arrow_up_rating,
                userId = communityDbEntity.id_user
            )
        }
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


    override fun onCleared() {
        super.onCleared()
        Log.d("MainViewModel", "ViewModel destroyed")
    }
}