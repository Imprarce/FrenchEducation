package com.imprarce.android.network.model.user

import java.time.LocalDateTime

data class User(
    var idUser: Int,
    var email: String,
    var hashPassword: String,
    var userName: String,
    var imageUrl: String,
    var dateCreateAcc: String
)
