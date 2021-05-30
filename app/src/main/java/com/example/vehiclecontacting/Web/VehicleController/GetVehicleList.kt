package com.example.vehiclecontacting.Web.VehicleController

data class GetVehicleList(
    val code: Int,
    val data: MyVehicleList,
    val msg: String
)

data class MyVehicleList(
    val vehicleList: List<MyVehicle>
)