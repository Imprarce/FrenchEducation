package com.imprarce.android.local.favorite_word

import com.imprarce.android.local.favorite_word.room.FavoriteWordDao
import com.imprarce.android.local.favorite_word.room.FavoriteWordDbEntity
import com.imprarce.android.local.favorite_word.room.FavoriteWordRepository

class FavoriteWordRepositoryImpl(private val favoriteWordDao: FavoriteWordDao) :
    FavoriteWordRepository {
    override suspend fun insertFavoriteWord(favoriteWord: FavoriteWordDbEntity) {
        favoriteWordDao.insertFavoriteWord(favoriteWord)
    }

    override suspend fun getFavoriteWordsByUserId(userId: String): List<FavoriteWordDbEntity> {
        return favoriteWordDao.getFavoriteWordsByUserId(userId)
    }
}