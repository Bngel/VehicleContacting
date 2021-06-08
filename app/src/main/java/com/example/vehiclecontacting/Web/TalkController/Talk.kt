package com.example.vehiclecontacting.Web.TalkController

data class Talk(
    val createTime: String,
    val fromId: String,
    val info: String,
    val number: String,
    val toId: String
)