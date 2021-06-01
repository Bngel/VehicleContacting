package com.example.vehiclecontacting.Web.DiscussController

data class MyHistory(
    val counts: Int,
    val historyList: List<Discuss>,
    val pages: Int
)