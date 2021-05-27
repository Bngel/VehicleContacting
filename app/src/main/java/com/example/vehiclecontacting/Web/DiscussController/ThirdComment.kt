package com.example.vehiclecontacting.Web.DiscussController

data class ThirdComment(
    val createTime: String,
    val description: String,
    val id: String,
    val likeCounts: Int,
    val number: String,
    val photo: String,
    val replyId: Any,
    val replyNumber: String,
    val replyUsername: Any,
    val username: String,
    val vip: Int
)