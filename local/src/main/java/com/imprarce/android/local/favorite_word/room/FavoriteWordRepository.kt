package com.imprarce.android.local.favorite_word.room

interface FavoriteWordRepository {
    suspend fun insertFavoriteWord(favoriteWord: FavoriteWordDbEntity)
    suspend fun getFavoriteWordsByUserId(userId: String): List<FavoriteWordDbEntity>
}