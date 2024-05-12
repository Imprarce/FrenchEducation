package com.imprarce.android.feature_community.createmessage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imprarce.android.local.community.room.CommunityDbEntity
import com.imprarce.android.local.community.room.CommunityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(
    private val communityRepository: CommunityRepository
) : ViewModel() {

    fun addNewMessage(idUser: Int, title: String, description: String, currentTime: String){
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
        }
    }
}