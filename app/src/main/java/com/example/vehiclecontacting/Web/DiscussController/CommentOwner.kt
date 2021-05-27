package com.example.vehiclecontacting.Web.DiscussController

data class CommentOwner(
    val commentCounts: Int,
    val comments: String,
    val createTime: String,
    val id: String,
    val likeCounts: Int,
    val number: String,
    val photo: String,
    val username: String,
    val vip: Int
)