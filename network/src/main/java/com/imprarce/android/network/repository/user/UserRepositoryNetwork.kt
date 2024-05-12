package com.imprarce.android.network.repository.user

import android.net.Uri
import com.imprarce.android.network.ResponseNetwork
import com.imprarce.android.network.model.user.User
import com.imprarce.android.network.model.user.UserLoginAndReg

interface UserRepositoryNetwork {

    suspend fun login(user: UserLoginAndReg): ResponseNetwork<String>

    suspend fun signUp(user: UserLoginAndReg): ResponseNetwork<String>

    suspend fun getUser(): ResponseNetwork<User>

    suspend fun logOut(): ResponseNetwork<String>

    suspend fun changeName(name: String)

    suspend fun getName(): String

    suspend fun changePhoto(photoUri: Uri)

    suspend fun getPhotoUrl(fileName: String): String

    suspend fun loadVideo(id_video: Int, videoUri: Uri)

    suspend fun getVideoUrl(id_video: Int): String

}