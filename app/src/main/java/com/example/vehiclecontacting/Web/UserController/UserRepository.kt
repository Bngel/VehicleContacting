package com.example.vehiclecontacting.Web.UserController

import com.example.vehiclecontacting.InfoRepository
import com.example.vehiclecontacting.StatusRepository
import com.example.vehiclecontacting.Web.WebService
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import java.lang.Exception
import javax.net.ssl.SSLEngineResult
import kotlin.concurrent.thread

object UserRepository {

    private val userService = WebService.create()

    private fun getNullUser(): User {
        return User("","",-1, -1, "", "", -1, "",
            "", "", "", "", "", "")
    }

    fun getUser(id: String): User {
        val data = userService.getUser(id)
        var user: User? = null
        try {
            thread {
                val body = data.execute().body()!!
                user = body.data.user
            }.join(4000)
        } catch (e: Exception) {}
        return if (user != null) user!! else getNullUser()
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
    fun postFindPassword(code: String, newPassword: String, phone: String){}

    /***
     * msg
     * codeExistWrong：验证码不存在或已失效
     * existWrong：账号不存在（用户没注册）
     * codeWrong：验证码错误（验证码可以不区分大小写）
     * frozenWrong：用户已被封号（返回json带frozenDate：封号截止时间）
     * success：成功（返回json带token：token令牌）
     */
    fun postLoginByCode(code: String, phone: String){}

    /***
     * msg:
     * existWrong：验证码不存在或已过期
     * codeWrong：验证码错误
     * repeatWrong：该手机已被绑定
     * success：成功
     */
    fun postRegister(code: String, password: String, phone: String){}

    /***
     * msg:
     * existWrong：用户不存在
     * fileWrong：文件为空
     * typeWrong：上传格式错误
     * success：成功，成功后返回json：url（头像url）
     */
    fun postUserPhoto(id: String, photo: MultipartBody.Part){}
}