package com.example.vehiclecontacting.Web.BoxController

import com.example.vehiclecontacting.Web.UserController.Data

data class DeleteBoxMessage(
    val code: Int,
    val data: Data,
    val msg: String
)
