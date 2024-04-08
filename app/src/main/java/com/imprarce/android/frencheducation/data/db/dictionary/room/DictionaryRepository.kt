package com.imprarce.android.frencheducation.data.db.dictionary.room

interface DictionaryRepository {
    suspend fun getAllWords(): List<DictionaryDbEntity>
    suspend fun insertWord(word: DictionaryDbEntity)
}