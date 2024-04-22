package com.imprarce.android.local.dictionary.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DictionaryDao {
    @Query("SELECT * FROM dictionary")
    suspend fun getAllWords(): List<DictionaryDbEntity>

    @Insert
    suspend fun insertWord(word: DictionaryDbEntity)
}