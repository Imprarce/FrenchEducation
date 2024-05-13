package com.imprarce.android.network.model.community

data class CommunitySend(
    var idCommunity: Int,
    var idUser: Int,
    var userImage: String,
    var userName: String,
    var title: String,
    var rating: Int,
    var view: Int,
    var hasProblemResolve: Boolean,
    var description: String
)
