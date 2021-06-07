package com.example.vehiclecontacting.Web.VehicleController

data class SearchVehicle(
    val counts: Int,
    val pages: Int,
    val vehicleList: List<SearchPerVehicle>
)
