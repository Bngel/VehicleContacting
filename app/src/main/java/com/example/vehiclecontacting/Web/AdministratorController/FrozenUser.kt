package com.example.vehiclecontacting.Web.AdministratorController

data class FrozenUser(
    val frozenDate: String,
    val id: String,
    val phone: String,
    val photo: String,
    val reopenDate: String,
    val username: String,
    val vip: Int
)