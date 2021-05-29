package com.example.vehiclecontacting.Web.AdministratorController

data class FrozenData (
    val counts: Int,
    val frozenUserList: List<FrozenUser>,
    val pages: Int
)