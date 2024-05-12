package com.imprarce.android.local.community

data class CommunityItem(
    val id_community: Int,
    val id_user: Int,
    val user_name: String,
    val user_image: String,
    val title: String,
    val rating: Int,
    val view: Int,
    val create_time: String,
    val last_change: String,
    val has_problem_resolve: Boolean,
    val description: String,
    val arrowRatingImageResource: Int
)