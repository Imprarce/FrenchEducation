package com.imprarce.android.local.dictionary.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "dictionary")
data class DictionaryDbEntity (
    @PrimaryKey(autoGenerate = true) var id_word: Int = 0,
    @ColumnInfo(name = "word_level") var level: Int,
    @ColumnInfo(name = "rus_translate") var wordRu: String,
    @ColumnInfo(name = "fr_translate") var wordFr: String
)