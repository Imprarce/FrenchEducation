package com.imprarce.android.local.user

import com.imprarce.android.local.ResponseRoom
import com.imprarce.android.local.user.room.UserDao
import com.imprarce.android.local.user.room.UserDbEntity
import com.imprarce.android.local.user.room.UserRepository

class UserRepositoryImpl(private val userDao: UserDao) : UserRepository {
    override suspend fun getAllUsers(): ResponseRoom<List<UserDbEntity>> {
        return try {
            val response = userDao.getAllUsers()
            ResponseRoom.Success(response)
        } catch (e: Exception){
            e.printStackTrace()
            ResponseRoom.Failure(e)
        }
    }

    override suspend fun getUserByEmail(email: String): ResponseRoom<UserDbEntity?> {
        return try {
            val response = userDao.getUserByEmail(email)
            ResponseRoom.Success(response)
        } catch (e: Exception){
            e.printStackTrace()
            ResponseRoom.Failure(e)
        }
    }

    override suspend fun insertUser(user: UserDbEntity): ResponseRoom<Unit> {
        return try {
            val response = userDao.insertUser(user)
            return ResponseRoom.Success(response)
        } catch (e: Exception){
            e.printStackTrace()
            ResponseRoom.Failure(e)
        }
    }

    override suspend fun getUserById(id_user: Int): ResponseRoom<UserDbEntity?> {
        return try {
            val response = userDao.getUserById(id_user)
            if(response != null){
                return ResponseRoom.Success(response)
            } else {
                return ResponseRoom.Failure(response)
            }
        } catch (e: Exception){
            e.printStackTrace()
            ResponseRoom.Failure(e)
        }
    }

    override suspend fun updateUserName(id_user: Int, name: String): ResponseRoom<Unit> {
        return try {
            val response = userDao.updateUserName(id_user, name)
            return ResponseRoom.Success(response)
        } catch (e: Exception){
            e.printStackTrace()
            ResponseRoom.Failure(e)
        }
    }

    override suspend fun updateUserPhoto(id_user: Int, photoUrl: String): ResponseRoom<Unit> {
        return try {
            val response = userDao.updateUserPhoto(id_user, photoUrl)
            return ResponseRoom.Success(response)
        } catch (e: Exception){
            e.printStackTrace()
            ResponseRoom.Failure(e)
        }
    }

    override suspend fun updateUser(user: UserDbEntity): ResponseRoom<Unit> {
        return try {
            val response = userDao.updateUser(user)
            return ResponseRoom.Success(response)
        } catch (e: Exception){
            e.printStackTrace()
            ResponseRoom.Failure(e)
        }
    }
}