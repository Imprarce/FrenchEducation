package com.imprarce.android.frencheducation.data.db.user

import com.imprarce.android.frencheducation.data.db.user.room.UserDao
import com.imprarce.android.frencheducation.data.db.user.room.UserDbEntity
import com.imprarce.android.frencheducation.data.db.user.room.UserRepository

class UserRepositoryImpl(private val userDao: UserDao) : UserRepository {
    override suspend fun getAllUsers(): List<UserDbEntity> {
        return userDao.getAllUsers()
    }

    override suspend fun getUserByLogin(login: String): UserDbEntity? {
        return userDao.getUserByLogin(login)
    }

    override suspend fun insertUser(user: UserDbEntity) {
        userDao.insertUser(user)
    }

    override suspend fun getUserById(id_user: String): UserDbEntity? {
        return userDao.getUserById(id_user)
    }

    override suspend fun updateUserName(id_user: String, name: String) {
        userDao.updateUserName(id_user, name)
    }

    override suspend fun updateUserPhoto(id_user: String, photoUrl: String) {
        userDao.updateUserPhoto(id_user, photoUrl)
    }

    override suspend fun updateUser(user: UserDbEntity) {
        userDao.updateUser(user)
    }
}