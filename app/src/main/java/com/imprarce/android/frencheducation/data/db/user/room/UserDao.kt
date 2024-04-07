package com.imprarce.android.frencheducation.data.db.user.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    suspend fun getAllUsers(): List<UserDbEntity>

    @Query("SELECT * FROM user WHERE login = :login")
    suspend fun getUserByLogin(login: String): UserDbEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserDbEntity)

}