package com.imprarce.android.local.dictionary.room

import com.imprarce.android.local.ResponseRoom

interface DictionaryRepository {
    suspend fun getAllWords(): ResponseRoom<List<DictionaryDbEntity>>
    suspend fun insertWord(word: DictionaryDbEntity): ResponseRoom<Unit>
}