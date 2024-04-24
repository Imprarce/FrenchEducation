package com.imprarce.android.local.favorite_word.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.imprarce.android.local.dictionary.room.DictionaryDbEntity
import com.imprarce.android.local.user.room.UserDbEntity


@Entity(
    tableName = "favorite_word",
    primaryKeys = ["id_user", "id_word"],
    foreignKeys = [
        ForeignKey(
            entity = UserDbEntity::class,
            parentColumns = ["id_user"],
            childColumns = ["id_user"]
        ),
        ForeignKey(
            entity = DictionaryDbEntity::class,
            parentColumns = ["id_word"],
            childColumns = ["id_word"]
        )
    ]
)
data class FavoriteWordDbEntity(
    @ColumnInfo(name = "id_user") val userId: String,
    @ColumnInfo(name = "id_word") val wordId: Int
)