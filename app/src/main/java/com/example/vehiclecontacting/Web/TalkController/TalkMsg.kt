package com.example.vehiclecontacting.Web.TalkController

data class TalkMsg(
    val id: String,
    val lastMessage: String,
    val noReadCounts: Int,
    val photo: String,
    val updateTime: String,
    val username: String,
    val vip: Int
)