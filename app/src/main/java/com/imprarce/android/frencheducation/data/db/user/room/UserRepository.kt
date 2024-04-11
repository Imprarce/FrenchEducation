package com.imprarce.android.frencheducation.data.db.user.room

import com.imprarce.android.frencheducation.data.db.ResponseRoom

interface UserRepository {
    suspend fun getAllUsers(): ResponseRoom<List<UserDbEntity>>
    suspend fun getUserByLogin(login: String): ResponseRoom<UserDbEntity?>
    suspend fun insertUser(user: UserDbEntity): ResponseRoom<Unit>
    suspend fun getUserById(id_user: String) : ResponseRoom<UserDbEntity?>
    suspend fun updateUserName(id_user: String, name: String): ResponseRoom<Unit>
    suspend fun updateUserPhoto(id_user: String, photoUrl: String): ResponseRoom<Unit>
    suspend fun updateUser(user: UserDbEntity): ResponseRoom<Unit>

}