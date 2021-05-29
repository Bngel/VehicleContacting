package com.example.vehiclecontacting.Web.AdministratorController

data class Vehicle(
    val backReason: String,
    val id: Long,
    val license: String,
    val sex: String,
    val type: Int,
    val updateTime: String,
    val userPhoto: String,
    val username: String,
    val vehiclePhoto: String,
    val vip: Int
)