package com.imprarce.android.feature_community.utils

import android.util.Log
import com.imprarce.android.feature_community.R
import com.imprarce.android.local.ResponseRoom
import com.imprarce.android.local.comment.CommentItem
import com.imprarce.android.local.comment.room.CommentDbEntity
import com.imprarce.android.local.community.CommunityItem
import com.imprarce.android.local.community.room.CommunityDbEntity
import com.imprarce.android.local.user.room.UserRepository
import com.imprarce.android.network.repository.user.UserRepositoryNetwork

class Converter (
    private val userRepository: UserRepository,
    private val userNetworkRepository: UserRepositoryNetwork
) {

    suspend fun convertToCommunityItemList(communityDbList: List<CommunityDbEntity>): List<CommunityItem> {
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

    suspend fun convertToCommunityItem(communityDb: CommunityDbEntity): CommunityItem {
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

    suspend fun convertToCommentsItemList(commentsDbList: List<CommentDbEntity>): List<CommentItem> {
        return commentsDbList.map { commentsDbEntity ->
            CommentItem(
                userId = commentsDbEntity.userId,
                userImage = getUserImage(commentsDbEntity.userId),
                userName = getUserName(commentsDbEntity.userId),
                message = commentsDbEntity.message
            )
        }
    }

    private suspend fun getUserName(id_user: Int): String{
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

    private suspend fun getUserImage(id_user: Int): String{
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

}