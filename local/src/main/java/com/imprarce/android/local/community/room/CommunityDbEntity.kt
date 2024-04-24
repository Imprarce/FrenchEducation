package com.imprarce.android.local.community.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.imprarce.android.local.user.room.UserDbEntity

@Entity(tableName = "community",
    foreignKeys = [
        ForeignKey(
            entity = UserDbEntity::class,
            parentColumns = ["id_user"],
            childColumns = ["id_user"],
        )
    ]
)
data class CommunityDbEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_community") val id_community: Int = 0,
    @ColumnInfo(name = "id_user") val id_user: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "rating") val rating: Int,
    @ColumnInfo(name = "view") val view: Int,
    @ColumnInfo(name = "create_time") val create_time: String,
    @ColumnInfo(name = "last_change") val last_change: String,
    @ColumnInfo(name = "has_problem_resolve") val has_problem_resolve: Boolean,
    @ColumnInfo(name = "description") val description: String
)