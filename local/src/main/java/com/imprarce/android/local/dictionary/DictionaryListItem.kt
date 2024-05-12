package com.imprarce.android.local.dictionary

import com.imprarce.android.local.dictionary.room.DictionaryDbEntity

data class DictionaryListItem (
    val id_word: Int,
    val level: Int,
    val wordRu: String,
    val wordFr: String
)