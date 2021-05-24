package com.example.vehiclecontacting.Web.DiscussController

data class Comments(
    val OwnerComment: OwnerComment,
    val commentList: List<Comment>,
    val counts: Int,
    val pages: Int
)
