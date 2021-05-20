package com.example.vehiclecontacting

import android.content.Context
import com.example.vehiclecontacting.Data.LoginStatusInfo
import com.example.vehiclecontacting.Web.UserController.User
import com.example.vehiclecontacting.Web.UserController.UserRepository

object InfoRepository {

    private const val LOCAL_STATUS = "localStatus"
    private const val KEY_STATUS = "userStatus"
    private const val KEY_ID = "userId"
    private const val KEY_PHONE = "userPhone"
    private const val KEY_TOKEN = "userToken"

    var user: User? = null
    var loginStatus = LoginStatusInfo(false, "", "", "")

    fun initUser(id: String) {
        user = UserRepository.getUser(id)
    }

    fun initStatus(context: Context){
        val localStatus = context.getSharedPreferences(LOCAL_STATUS, Context.MODE_PRIVATE)
        val status = localStatus.getBoolean(KEY_STATUS, false)
        if (status) {
            // 用户处于登录状态 可以直接 getUser
            val id = localStatus.getString(KEY_ID, "")!!
            val phone = localStatus.getString(KEY_PHONE, "")!!
            val token = localStatus.getString(KEY_TOKEN, "")!!
            loginStatus = LoginStatusInfo(status, id, phone, token)
            initUser(id)
        }
    }
}