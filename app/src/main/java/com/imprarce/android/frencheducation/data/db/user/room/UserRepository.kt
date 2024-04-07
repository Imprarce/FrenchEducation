package com.imprarce.android.frencheducation.data.db.user.room

interface UserRepository {
    suspend fun getAllUsers(): List<UserDbEntity>
    suspend fun getUserByLogin(login: String): UserDbEntity?
    suspend fun insertUser(user: UserDbEntity)
}