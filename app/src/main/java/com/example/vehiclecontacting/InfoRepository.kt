package com.example.vehiclecontacting

import com.example.vehiclecontacting.Web.UserController.User
import com.example.vehiclecontacting.Web.UserController.UserRepository

object InfoRepository {

    var user: User? = null

    fun initUser(id: String) {
        user = UserRepository.getUser(id)
    }
}