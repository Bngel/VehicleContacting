package com.example.vehiclecontacting.Web.UserController

data class PostLoginByCode(
    val code: Int,
    val data: Data,
    val msg: String
)
