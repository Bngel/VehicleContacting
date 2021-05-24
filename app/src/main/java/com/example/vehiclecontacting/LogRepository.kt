package com.example.vehiclecontacting

import android.util.Log
import com.example.vehiclecontacting.Web.DiscussController.GetComment
import com.example.vehiclecontacting.Web.DiscussController.GetDiscuss
import com.example.vehiclecontacting.Web.DiscussController.PostDiscuss
import com.example.vehiclecontacting.Web.DiscussController.PostDiscussPhoto
import com.example.vehiclecontacting.Web.UserController.PostLogin
import com.example.vehiclecontacting.Web.UserController.PostLoginByCode
import com.example.vehiclecontacting.Web.UserController.PostRegister
import com.example.vehiclecontacting.Web.UserController.PostUserPhoto
import java.lang.StringBuilder

object LogRepository {

    fun loginLog(body: PostLoginByCode) {
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
            else -> {
                stringBuilder.append("-\tmsg: 登录发生未知错误\t-\n")
            }
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
            else -> {
                stringBuilder.append("-\tmsg: 登录发生未知错误\t-\n")
            }
        }
        Log.d(StatusRepository.VehicleLog, stringBuilder.toString())
    }

    fun updateAvtLog(body: PostUserPhoto) {
        val stringBuilder = StringBuilder()
        if (body.code == 200) {
            stringBuilder.append("-\t修改头像接口访问成功\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        else {
            stringBuilder.append("-\t修改头像接口访问失败\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        when (body.msg) {
            "existWrong" -> {
                stringBuilder.append("-\tmsg: 用户不存在\t-\n")
            }
            "fileWrong" -> {
                stringBuilder.append("-\tmsg: 文件为空\t-\n")
            }
            "success" -> {
                stringBuilder.append("-\tmsg: 上传成功\t-\n")
                stringBuilder.append("-\turl: ${body.data}\t-\n")
            }
            else -> {
                stringBuilder.append("-\tmsg: 访问接口发生未知错误\t-\n")
            }
        }
        Log.d(StatusRepository.VehicleLog, stringBuilder.toString())
    }

    fun updateDiscussPhotoLog (body: PostDiscussPhoto) {
        val stringBuilder = StringBuilder()
        if (body.code == 200) {
            stringBuilder.append("-\t上传帖子图片接口访问成功\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        else {
            stringBuilder.append("-\t上传帖子图片接口访问失败\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        when (body.msg) {
            "typeWrong" -> {
                stringBuilder.append("-\tmsg: 文件类型错误\t-\n")
            }
            "fileWrong" -> {
                stringBuilder.append("-\tmsg: 文件为空\t-\n")
            }
            "success" -> {
                stringBuilder.append("-\tmsg: 上传成功\t-\n")
                stringBuilder.append("-\turl: ${body.data}\t-\n")
            }
            else -> {
                stringBuilder.append("-\tmsg: 访问接口发生未知错误\t-\n")
            }
        }
        Log.d(StatusRepository.VehicleLog, stringBuilder.toString())
    }

    fun sendDiscussLog (body: PostDiscuss) {
        val stringBuilder = StringBuilder()
        if (body.code == 200) {
            stringBuilder.append("-\t发表帖子接口访问成功\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        else {
            stringBuilder.append("-\t发表帖子接口访问失败\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        when (body.msg) {
            "repeatWrong" -> {
                stringBuilder.append("-\tmsg: 该用户短期创建新帖子太多\t-\n")
            }
            "success" -> {
                stringBuilder.append("-\tmsg: 发表成功\t-\n")
            }
            else -> {
                stringBuilder.append("-\tmsg: 访问接口发生未知错误\t-\n")
            }
        }
        Log.d(StatusRepository.VehicleLog, stringBuilder.toString())
    }

    fun getDiscussLog (body: GetDiscuss) {
        val stringBuilder = StringBuilder()
        if (body.code == 200) {
            stringBuilder.append("-\t获取帖子接口访问成功\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        else {
            stringBuilder.append("-\t获取帖子接口访问失败\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        when (body.msg) {
            "success" -> {
                stringBuilder.append("-\tmsg: 获取成功\t-\n")
                for (discuss in body.data.discussList)
                    stringBuilder.append("-\ttitle: ${discuss.title}\t-\n")
            }
            else -> {
                stringBuilder.append("-\tmsg: 访问接口发生未知错误\t-\n")
            }
        }
        Log.d(StatusRepository.VehicleLog, stringBuilder.toString())
    }

    fun getCommentLog (body: GetComment) {
        val stringBuilder = StringBuilder()
        if (body.code == 200) {
            stringBuilder.append("-\t获取评论一级界面接口访问成功\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        else {
            stringBuilder.append("-\t获取评论一级界面接口访问失败\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        when (body.msg) {
            "success" -> {
                stringBuilder.append("-\tmsg: 获取成功\t-\n")
                stringBuilder.append("-\ttitle: ${body.data.OwnerComment.title}\t-\n")
            }
            "existWrong" -> {
                stringBuilder.append("-\tmsg: 帖子不存在\t-\n")
            }
            else -> {
                stringBuilder.append("-\tmsg: 访问接口发生未知错误\t-\n")
            }
        }
        Log.d(StatusRepository.VehicleLog, stringBuilder.toString())
    }
}