package com.imprarce.android.frencheducation.data.db.dictionary

import com.imprarce.android.frencheducation.data.db.dictionary.room.DictionaryDao
import com.imprarce.android.frencheducation.data.db.dictionary.room.DictionaryDbEntity
import com.imprarce.android.frencheducation.data.db.dictionary.room.DictionaryRepository

class DictionaryRepositoryImpl(private val dictionaryDao: DictionaryDao) : DictionaryRepository {
    override suspend fun getAllWords(): List<DictionaryDbEntity> {
        return dictionaryDao.getAllWords()
    }

    override suspend fun insertWord(word: DictionaryDbEntity) {
        dictionaryDao.insertWord(word)
    }
}