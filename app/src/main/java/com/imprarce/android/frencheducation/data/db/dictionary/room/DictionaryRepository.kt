package com.imprarce.android.frencheducation.data.db.dictionary.room

import com.imprarce.android.frencheducation.data.db.ResponseRoom

interface DictionaryRepository {
    suspend fun getAllWords(): ResponseRoom<List<DictionaryDbEntity>>
    suspend fun insertWord(word: DictionaryDbEntity): ResponseRoom<Unit>
}