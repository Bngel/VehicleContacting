package com.example.vehiclecontacting.Web.AdministratorController

import com.example.vehiclecontacting.Web.UserController.Data

data class PostJudgeVehicle(
    val code: Int,
    val data: Data,
    val msg: String
)
