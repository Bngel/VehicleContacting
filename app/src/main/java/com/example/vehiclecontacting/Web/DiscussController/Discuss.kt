package com.example.vehiclecontacting.Web.DiscussController

import android.os.Parcel
import android.os.Parcelable

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