package com.example.vehiclecontacting.Web.UserController

import android.util.Log
import com.example.vehiclecontacting.Data.LoginStatusInfo
import com.example.vehiclecontacting.InfoRepository
import com.example.vehiclecontacting.LogRepository
import com.example.vehiclecontacting.StatusRepository
import com.example.vehiclecontacting.Web.WebService
import com.google.gson.GsonBuilder
import okhttp3.MultipartBody
import java.lang.Exception
import kotlin.concurrent.thread

object UserRepository {

    private val userService = WebService.create()

    private fun getNullUser(msg: String): User {
        return User("",msg,"", "", "",
            "", "", "", -1, -1, -1, -1,
            -1,"", "", "", "", -1)
    }

    fun getUser(id: String): User {
        val data = userService.getUser(id)
        var user: User? = null
        var msg = ""
        try {
            thread {
                val body = data.execute().body()!!
                user = body.data.user
                msg = body.msg
            }.join(4000)
        } catch (e: Exception) {}
        return if (user != null) user!! else getNullUser(msg)
    }

    fun patchUser(id: Int, sex: String ="", username: String=""): Boolean {
        val data = userService.patchUser(id, sex, username)
        var msg = ""
        try {
            thread {
                val body = data.execute().body()!!
                msg = body.msg
            }.join(4000)
        } catch (e: Exception) {}
        return msg == "success"
    }

    /**
     * msg:
     * existWrong：用户不存在
     * oldPasswordWrong：旧密码错误
     * codeExistWrong：验证码不存在或已过期
     * codeWrong：验证码错误
     * success：成功
     */
    fun postChangePassword(code: String, newPassword: String, oldPassword: String, phone: String): Int {
        val data = userService.postChangePassword(code, newPassword, oldPassword, phone)
        var msg = ""
        try {
            thread {
                val body = data.execute().body()!!
                msg = body.msg
            }.join(4000)
        } catch (e: Exception) {}
        return when (msg) {
            "existWrong" -> StatusRepository.EXIST_WRONG
            "oldPasswordWrong" -> StatusRepository.OLD_PASSWORD_WRONG
            "codeExistWrong" -> StatusRepository.CODE_EXIST_WRONG
            "codeWrong" -> StatusRepository.CODE_WRONG
            "success" -> StatusRepository.SUCCESS
            else -> StatusRepository.UNKNOWN_WRONG
        }
    }

    /***
     * msg:
     * repeatWrong：获取验证码次数过多
     * existWrong：手机号不存在（验证码发送错误）
     * success：成功
     */

    const val TYPE_REGISTER = 1
    const val TYPE_CHANGE = 2
    const val TYPE_FIND = 3
    const val TYPE_LOGIN = 4

    fun postCode(phone: String, type: Int): Int {
        val data = userService.postCode(phone, type)
        var msg = ""
        try {
            thread {
                val body = data.execute().body()!!
                msg = body.msg
            }.join(4000)
        } catch (e: Exception) {}
        return when (msg) {
            "repeatWrong" -> StatusRepository.REPEAT_WRONG
            "existWrong" -> StatusRepository.EXIST_WRONG
            "success" -> StatusRepository.SUCCESS
            else -> StatusRepository.UNKNOWN_WRONG
        }
    }

    /***
     * msg:
     * existWrong：用户不存在
     * codeExistWrong：验证码不存在或已过期
     * codeWrong：验证码错误
     * success：成功
     */
    fun postFindPassword(code: String, newPassword: String, phone: String): Int{
        val data = userService.postFindPassword(code, newPassword, phone)
        var msg = ""
        try {
            thread {
                val body = data.execute().body()!!
                msg = body.msg
            }.join(4000)
        } catch (e: Exception) {}
        return when (msg) {
            "existWrong" -> StatusRepository.EXIST_WRONG
            "codeExistWrong" -> StatusRepository.CODE_EXIST_WRONG
            "codeWrong" -> StatusRepository.CODE_WRONG
            "success" -> StatusRepository.SUCCESS
            else -> StatusRepository.UNKNOWN_WRONG
        }
    }

    /***
     * msg
     * codeExistWrong：验证码不存在或已失效
     * existWrong：账号不存在（用户没注册）
     * codeWrong：验证码错误（验证码可以不区分大小写）
     * frozenWrong：用户已被封号（返回json带frozenDate：封号截止时间）
     * success：成功（返回json带token：token令牌）
     */
    fun postLoginByCode(code: String, phone: String): Int{
        val gson = GsonBuilder()
            .registerTypeAdapter(PostLoginByCode::class.java, PostLoginByCode.DataStateDeserializer())
            .setLenient()
            .create()
        val loginService = WebService.create(gson)
        val data = loginService.postLoginByCode(code, phone)
        var msg = ""
        try {
            thread {
                val body = data.execute().body()!!
                LogRepository.loginLog(body)
                msg = body.msg
                if (msg == "success") {
                    InfoRepository.loginStatus.token = body.data.toString()
                }
            }.join(4000)
        } catch (e: Exception) {}
        return when (msg) {
            "existWrong" -> StatusRepository.EXIST_WRONG
            "codeExistWrong" -> StatusRepository.CODE_EXIST_WRONG
            "codeWrong" -> StatusRepository.CODE_WRONG
            "frozenWrong" -> StatusRepository.FROZEN_WRONG
            "success" -> {
                StatusRepository.SUCCESS
            }
            else -> StatusRepository.UNKNOWN_WRONG
        }
    }

    /***
     * msg
     * codeExistWrong：验证码不存在或已失效
     * existWrong：账号不存在（用户没注册）
     * codeWrong：验证码错误（验证码可以不区分大小写）
     * frozenWrong：用户已被封号（返回json带frozenDate：封号截止时间）
     * success：成功（返回json带token：token令牌）
     */
    fun postLogin(phone: String, password: String): Int{
        val gson = GsonBuilder()
            .registerTypeAdapter(PostLogin::class.java, PostLogin.DataStateDeserializer())
            .setLenient()
            .create()
        val loginService = WebService.create(gson)
        val data = loginService.postLogin(phone, password)
        var msg = ""
        try {
            thread {
                val body = data.execute().body()!!
                LogRepository.loginLog(body)
                msg = body.msg
            }.join(4000)
        } catch (e: Exception) {}
        return when (msg) {
            "existWrong" -> StatusRepository.EXIST_WRONG
            "codeExistWrong" -> StatusRepository.CODE_EXIST_WRONG
            "userWrong" -> StatusRepository.CODE_WRONG
            "frozenWrong" -> StatusRepository.FROZEN_WRONG
            "success" -> StatusRepository.SUCCESS
            else -> StatusRepository.UNKNOWN_WRONG
        }
    }

    /***
     * msg:
     * existWrong：用户不存在
     * fileWrong：文件为空
     * typeWrong：上传格式错误
     * success：成功，成功后返回json：url（头像url）
     */
    fun postUserPhoto(id: String, photo: MultipartBody.Part): Int{
        val gson = GsonBuilder()
            .registerTypeAdapter(PostUserPhoto::class.java, PostUserPhoto.DataStateDeserializer())
            .setLenient()
            .create()
        val photoService = WebService.create(gson)
        val data = photoService.postUserPhoto(id, photo)
        var msg = ""
        try {
            thread {
                val body = data.execute().body()!!
                LogRepository.updateAvtLog(body)
                msg = body.msg
            }.join(4000)
        } catch (e: Exception) {}
        return when (msg) {
            "existWrong" -> StatusRepository.EXIST_WRONG
            "fileWrong" -> StatusRepository.FILE_WRONG
            "typeWrong" -> StatusRepository.TYPE_WRONG
            "success" -> StatusRepository.SUCCESS
            else -> StatusRepository.UNKNOWN_WRONG
        }
    }
}