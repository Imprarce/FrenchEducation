package com.imprarce.android.feature_login.helpers

import com.imprarce.android.local.user.room.UserDbEntity
import com.imprarce.android.network.model.user.User

class ConverterUser {
    fun convertToDbEntity(user: User): UserDbEntity {
        return UserDbEntity(
            id_user = user.idUser,
            email = user.email,
            password = user.hashPassword,
            userName = user.userName,
            imageUrl = user.imageUrl,
            dateCreateAcc = user.dateCreateAcc
        )
    }
}