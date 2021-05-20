package com.example.vehiclecontacting.Web.UserController

data class User(
    val createTime: String,
    val email: String,
    val fansCounts: Int,
    val followCounts: Int,
    val frozenDate: String,
    val id: String,
    val isFrozen: Int,
    val password: String,
    val phone: String,
    val photo: String,
    val reopenDate: String,
    val sex: String,
    val updateTime: String,
    val username: String
)