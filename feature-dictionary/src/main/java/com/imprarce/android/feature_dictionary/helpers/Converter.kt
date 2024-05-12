package com.imprarce.android.feature_dictionary.helpers

import com.imprarce.android.local.dictionary.DictionaryListItem
import com.imprarce.android.local.dictionary.room.DictionaryDbEntity
import com.imprarce.android.network.model.dictionary.DictionaryWord

object Converter {
    fun toDictionaryListItem(dictionaryWord: DictionaryWord): DictionaryListItem {
        return DictionaryListItem(
            id_word = dictionaryWord.idWord,
            level = dictionaryWord.wordLevel,
            wordRu = dictionaryWord.wordRu,
            wordFr = dictionaryWord.wordFr
        )
    }

    fun toDictionaryListItems(dictionaryWords: List<DictionaryWord>): List<DictionaryListItem> {
        return dictionaryWords.map { toDictionaryListItem(it) }
    }

    fun toDictionaryListItem(dictionaryDbEntity: DictionaryDbEntity): DictionaryListItem {
        return DictionaryListItem(
            id_word = dictionaryDbEntity.id_word,
            level = dictionaryDbEntity.level,
            wordRu = dictionaryDbEntity.wordRu,
            wordFr = dictionaryDbEntity.wordFr
        )
    }
}