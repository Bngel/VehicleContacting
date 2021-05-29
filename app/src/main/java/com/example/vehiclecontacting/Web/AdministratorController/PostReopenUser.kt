package com.example.vehiclecontacting.Web.AdministratorController

import com.example.vehiclecontacting.Web.UserController.Data

data class PostReopenUser(
    val code: Int,
    val data: Data,
    val msg: String
)
