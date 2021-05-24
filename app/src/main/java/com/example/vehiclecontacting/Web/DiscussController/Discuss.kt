package com.example.vehiclecontacting.Web.DiscussController

data class Discuss(
    val commentCounts: Int,
    val description: String,
    val favorCounts: Int,
    val likeCounts: Int,
    val number: String,
    val photo: String,
    val scanCounts: Int,
    val title: String,
    val updateTime: String,
    val userPhoto: String,
    val username: String
)