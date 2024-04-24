package com.imprarce.android.local.favorite_word.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteWordDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavoriteWord(favoriteWord: FavoriteWordDbEntity)

    @Query("SELECT * FROM favorite_word WHERE id_user = :userId")
    suspend fun getFavoriteWordsByUserId(userId: String): List<FavoriteWordDbEntity>

}