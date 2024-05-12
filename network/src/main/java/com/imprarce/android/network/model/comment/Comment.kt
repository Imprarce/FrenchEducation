package com.imprarce.android.network.model.comment

data class Comment(
    var idComment: Int,
    var idUser: Int,
    var idCommunity: Int,
    var rating: Int,
    var message: String
)
