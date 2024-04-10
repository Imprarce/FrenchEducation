package com.imprarce.android.frencheducation.data.db.user.room

interface UserRepository {
    suspend fun getAllUsers(): List<UserDbEntity>
    suspend fun getUserByLogin(login: String): UserDbEntity?
    suspend fun insertUser(user: UserDbEntity)
    suspend fun getUserById(id_user: String) : UserDbEntity?
    suspend fun updateUserName(id_user: String, name: String)
    suspend fun updateUserPhoto(id_user: String, photoUrl: String)
    suspend fun updateUser(user: UserDbEntity)

}