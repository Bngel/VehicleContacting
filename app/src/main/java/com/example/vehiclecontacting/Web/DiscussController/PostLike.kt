package com.example.vehiclecontacting.Web.DiscussController

import com.example.vehiclecontacting.Web.UserController.Data

data class PostLike(
    val code: Int,
    val data: Data,
    val msg: String
)