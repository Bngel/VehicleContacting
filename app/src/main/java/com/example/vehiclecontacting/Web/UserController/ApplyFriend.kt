package com.example.vehiclecontacting.Web.UserController

data class ApplyFriend(
    val counts: Int,
    val pages: Int,
    val postFriendList: List<PerApplyFriend>
)
