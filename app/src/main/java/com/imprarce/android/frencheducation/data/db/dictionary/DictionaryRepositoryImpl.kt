package com.imprarce.android.frencheducation.data.db.dictionary

import com.imprarce.android.frencheducation.data.db.ResponseRoom
import com.imprarce.android.frencheducation.data.db.dictionary.room.DictionaryDao
import com.imprarce.android.frencheducation.data.db.dictionary.room.DictionaryDbEntity
import com.imprarce.android.frencheducation.data.db.dictionary.room.DictionaryRepository

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