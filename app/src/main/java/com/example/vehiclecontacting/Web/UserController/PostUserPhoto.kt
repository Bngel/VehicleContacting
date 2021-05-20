package com.example.vehiclecontacting.Web.UserController

data class PostUserPhoto(
    val error: String,
    val message: String,
    val path: String,
    val status: Int,
    val timestamp: String
)