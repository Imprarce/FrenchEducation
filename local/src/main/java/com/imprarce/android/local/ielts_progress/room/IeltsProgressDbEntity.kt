package com.imprarce.android.local.ielts_progress.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.imprarce.android.local.ielts.room.IeltsDbEntity
import com.imprarce.android.local.user.room.UserDbEntity


@Entity(
    tableName = "ielts_progress",
    primaryKeys = ["id_ielts", "id_user"],
    foreignKeys = [
        ForeignKey(
            entity = IeltsDbEntity::class,
            parentColumns = ["id_ielts"],
            childColumns = ["id_ielts"]
        ),
        ForeignKey(
            entity = UserDbEntity::class,
            parentColumns = ["id_user"],
            childColumns = ["id_user"]
        )
    ]
)
data class IeltsProgressDbEntity(
    @ColumnInfo(name = "id_ielts") val ieltsId: Int,
    @ColumnInfo(name = "id_user") val userId: String
)