package com.imprarce.android.local.comment.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.imprarce.android.local.community.room.CommunityDbEntity
import com.imprarce.android.local.user.room.UserDbEntity


@Entity(tableName = "comment",
    foreignKeys = [
        ForeignKey(
            entity = UserDbEntity::class,
            parentColumns = ["id_user"],
            childColumns = ["id_user"],
        ),
        ForeignKey(
            entity = CommunityDbEntity::class,
            parentColumns = ["id_community"],
            childColumns = ["id_community"],
        )
    ]
)
data class CommentDbEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_comment") val id_comment: Int = 0,
    @ColumnInfo(name = "id_user") val userId: Int,
    @ColumnInfo(name = "id_community") val communityId: Int,
    @ColumnInfo(name = "message") val message: String,
    @ColumnInfo(name = "rating") val rating: Int
)