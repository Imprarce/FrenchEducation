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

}