package com.example.vehiclecontacting.Web.DiscussController

import com.example.vehiclecontacting.Web.UserController.Data

data class PostComment(
    val code: Int,
    val data: Data,
    val msg: String
)
