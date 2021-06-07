package com.example.vehiclecontacting.Web.VehicleController

data class SearchPerVehicle(
    val id: String,
    val license: String,
    val passTime: String,
    val photo: String,
    val sex: String,
    val type: Int,
    val username: String,
    val vehicleBrand: String,
    val vip: Int
)
