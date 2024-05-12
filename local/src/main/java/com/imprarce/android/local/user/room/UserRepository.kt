package com.imprarce.android.local.user.room

import com.imprarce.android.local.ResponseRoom

interface UserRepository {
    suspend fun getAllUsers(): ResponseRoom<List<UserDbEntity>>
    suspend fun getUserByEmail(email: String): ResponseRoom<UserDbEntity?>
    suspend fun insertUser(user: UserDbEntity): ResponseRoom<Unit>
    suspend fun getUserById(id_user: Int) : ResponseRoom<UserDbEntity?>
    suspend fun updateUserName(id_user: Int, name: String): ResponseRoom<Unit>
    suspend fun updateUserPhoto(id_user: Int, photoUrl: String): ResponseRoom<Unit>
    suspend fun updateUser(user: UserDbEntity): ResponseRoom<Unit>

}