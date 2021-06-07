package com.example.vehiclecontacting.Web.UserController

data class SearchUser(
    val counts: Int,
    val pages: Int,
    val userList: List<SearchPerUser>
)
