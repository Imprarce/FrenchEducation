package com.imprarce.android.local.user.room

import androidx.room.*

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    suspend fun getAllUsers(): List<UserDbEntity>

    @Query("SELECT * FROM user WHERE email = :email")
    suspend fun getUserByEmail(email: String): UserDbEntity?

    @Query("SELECT * FROM user WHERE id_user = :id_user")
    suspend fun getUserById(id_user: Int): UserDbEntity

    @Query("UPDATE user SET user_name = :name WHERE id_user = :id_user")
    suspend fun updateUserName(id_user: Int, name: String)

    @Query("UPDATE user SET image_url = :photoUri WHERE id_user = :userId")
    suspend fun updateUserPhoto(userId: Int, photoUri: String)

    @Update
    suspend fun updateUser(user: UserDbEntity)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserDbEntity)

}