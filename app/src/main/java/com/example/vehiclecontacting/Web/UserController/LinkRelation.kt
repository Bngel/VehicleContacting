package com.example.vehiclecontacting.Web.UserController

data class LinkRelation(
    val createTime: String,
    val id: String,
    val introduction: String,
    val license1: String?,
    val license2: String?,
    val license3: String?,
    val license4: String?,
    val photo: String,
    val relationship: String,
    val sex: String,
    val username: String
)