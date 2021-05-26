package com.example.vehiclecontacting.Web.UserController

data class FollowList(
    val counts: Int,
    val followList: List<Follow>,
    val pages: Int
)
