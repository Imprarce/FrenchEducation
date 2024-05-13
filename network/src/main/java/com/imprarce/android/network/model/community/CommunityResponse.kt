package com.imprarce.android.network.model.community

import java.time.LocalDateTime

data class CommunityResponse(
    var idCommunity: Int,
    var idUser: Int,
    var userImage: String,
    var userName: String,
    var title: String,
    var rating: Int,
    var view: Int,
    var createTime: String,
    var lastChange: String,
    var hasProblemResolve: Boolean,
    var description: String
)
