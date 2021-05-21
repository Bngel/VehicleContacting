package com.example.vehiclecontacting

import android.util.Log
import com.example.vehiclecontacting.Web.UserController.PostLogin
import com.example.vehiclecontacting.Web.UserController.PostLoginByCode
import java.lang.StringBuilder

object LogRepository {

    fun loginLog(body: PostLoginByCode) {
        val stringBuilder = StringBuilder()
        if (body.code == 200) {
            stringBuilder.append("\n" +
                    "-\t登录接口访问成功\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
            when (body.msg) {
                "codeExistWrong" -> {
                    stringBuilder.append("-\tmsg: 验证码不存在或已失效\t-\n")
                }
                "existWrong" -> {
                    stringBuilder.append("-\tmsg: 账号不存在\t-\n")
                }
                "codeWrong" -> {
                    stringBuilder.append("-\tmsg: 验证码错误\t-\n")
                }
                "frozenWrong" -> {
                    stringBuilder.append("-\tmsg: 用户已被封号 封禁时间: ${body.data}\t-\n")
                }
                "success" -> {
                    stringBuilder.append("-\tmsg: 登录成功 token: ${body.data}\t-\n")
                }
            }
        }
        else {
            stringBuilder.append("\n" +
                    "-\t登录接口访问失败\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        Log.d(StatusRepository.VehicleLog, stringBuilder.toString())
    }

    fun loginLog(body: PostLogin) {
        val stringBuilder = StringBuilder()
        if (body.code == 200) {
            stringBuilder.append("-\t登录接口访问成功\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        else {
            stringBuilder.append("-\t登录接口访问失败\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        when (body.msg) {
            "codeExistWrong" -> {
                stringBuilder.append("-\tmsg: 验证码不存在或已失效\t-\n")
            }
            "existWrong" -> {
                stringBuilder.append("-\tmsg: 账号不存在\t-\n")
            }
            "userWrong" -> {
                stringBuilder.append("-\tmsg: 用户名或密码错误\t-\n")
            }
            "frozenWrong" -> {
                stringBuilder.append("-\tmsg: 用户已被封号\t-\n")
                stringBuilder.append("-\t封禁时间: ${body.data}\t-\n")
            }
            "success" -> {
                stringBuilder.append("-\tmsg: 登录成功\t-\n")
                stringBuilder.append("-\ttoken: ${body.data}\t-\n")
            }
        }
        Log.d(StatusRepository.VehicleLog, stringBuilder.toString())
    }
}