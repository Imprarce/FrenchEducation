package com.imprarce.android.frencheducation.data.db.user.room

import androidx.room.*

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    suspend fun getAllUsers(): List<UserDbEntity>

    @Query("SELECT * FROM user WHERE login = :login")
    suspend fun getUserByLogin(login: String): UserDbEntity?

    @Query("SELECT * FROM user WHERE id_user = :id_user")
    suspend fun getUserById(id_user: String): UserDbEntity?

    @Query("UPDATE user SET user_name = :name WHERE id_user = :id_user")
    suspend fun updateUserName(id_user: String, name: String)

    @Query("UPDATE user SET image_url = :photoUri WHERE id_user = :userId")
    suspend fun updateUserPhoto(userId: String, photoUri: String)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserDbEntity)

}