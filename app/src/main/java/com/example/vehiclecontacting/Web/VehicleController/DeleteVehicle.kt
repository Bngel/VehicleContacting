package com.example.vehiclecontacting.Web.VehicleController

import com.example.vehiclecontacting.Web.UserController.Data

data class DeleteVehicle(
    val code: Int,
    val data: Data,
    val msg: String
)
