package com.imprarce.android.local.dictionary

import com.imprarce.android.local.ResponseRoom
import com.imprarce.android.local.dictionary.room.DictionaryDao
import com.imprarce.android.local.dictionary.room.DictionaryDbEntity
import com.imprarce.android.local.dictionary.room.DictionaryRepository

class DictionaryRepositoryImpl(private val dictionaryDao: DictionaryDao) : DictionaryRepository {
    override suspend fun getAllWords(): ResponseRoom<List<DictionaryDbEntity>> {
        return try {
            val result = dictionaryDao.getAllWords()
            ResponseRoom.Success(result)
        } catch (e: Exception) {
            ResponseRoom.Failure(e)
        }
    }

    override suspend fun insertWord(word: DictionaryDbEntity): ResponseRoom<Unit> {
        return try {
            dictionaryDao.insertWord(word)
            ResponseRoom.Success(Unit)
        } catch (e: Exception) {
            ResponseRoom.Failure(e)
        }
    }
}