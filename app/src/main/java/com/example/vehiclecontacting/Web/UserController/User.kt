package com.example.vehiclecontacting.Web.UserController

data class User(
    val id: String,
    val username: String,
    val password: String,
    val phone: String,
    val sex: String,
    val photo: String,
    val email: String,
    val introduction: String,
    val fansCounts: Int,
    val followCounts: Int,
    val discussCounts: Int,
    val momentCounts: Int,
    val isFrozen: Int,
    val frozenDate: String,
    val createTime: String,
    val reopenDate: String,
    val updateTime: String,
    val complainCounts: Int,
    val vip: Int,
    val dirtyCounts: Int,
    val noSpeakDate: String,
    val blackCounts: Int,
    val friendCounts: Int,
    val boxMessageCounts: Int
)