package com.imprarce.android.local.video.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.imprarce.android.local.user.room.UserDbEntity


@Entity(
    tableName = "video",
    foreignKeys = [
        ForeignKey(
            entity = UserDbEntity::class,
            parentColumns = ["id_user"],
            childColumns = ["id_user"]
        )
    ]
)
data class VideoDbEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id_video") val videoId: Int = 0,
    @ColumnInfo(name = "id_user") val userId: String,
    @ColumnInfo(name = "rating") val rating: Int,
    @ColumnInfo(name = "view") val view: Int,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "video_file") val videoFile: String,
    @ColumnInfo(name = "image_url") val imageUrl: String?
)
