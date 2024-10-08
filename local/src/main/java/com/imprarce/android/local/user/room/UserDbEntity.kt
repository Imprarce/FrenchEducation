package com.imprarce.android.local.user.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "user")
data class UserDbEntity(
    @PrimaryKey var id_user: Int,
    @ColumnInfo(name = "password") var password: String,
    @ColumnInfo(name = "email") var email: String,
    @ColumnInfo(name = "user_name") var userName: String?,
    @ColumnInfo(name = "image_url") var imageUrl: String?,
    @ColumnInfo(name = "date_create_acc") var dateCreateAcc: String
)