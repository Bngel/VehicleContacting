package com.example.vehiclecontacting.Web.UserController

import com.example.vehiclecontacting.Repository.InfoRepository
import com.example.vehiclecontacting.Repository.LogRepository
import com.example.vehiclecontacting.Repository.StatusRepository
import com.example.vehiclecontacting.Web.WebService
import com.google.gson.GsonBuilder
import okhttp3.MultipartBody
import java.lang.Exception
import kotlin.concurrent.thread

object UserRepository {

    private val userService = WebService.create()

    const val FOLLOW_NOT = 1
    const val FOLLOW_ED = 2
    const val FOLLOW_BOTH = 3
    var followStatus = FOLLOW_NOT
    val followList = ArrayList<Follow>()
    var followPage = 0
    var followCount = 0
    val fansList = ArrayList<Fans>()
    var fansPage = 0
    var fansCount = 0

    private fun getNullUser(msg: String): User {
        return User("",msg,"", "", "",
            "", "", "", -1, -1, -1, -1,
            -1,"", "", "", "", -1, -1, -1, "",-1)
    }

    fun getUser(phone: String): User {
        val data = userService.getUser(phone)
        var user: User? = null
        var msg = ""
        try {
            thread {
                val body = data.execute().body()!!
                user = body.data.user
                msg = body.msg
                LogRepository.getUserLog(body)
            }.join(4000)
        } catch (e: Exception) {}
        return if (user != null) user!! else getNullUser(msg)
    }

    fun getUserById(id: String): User {
        val data = userService.getUserById(id)
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

    /**
     * msg:
     * existWrong：用户不存在
     * repeatWrong：用户没有修改信息（可能是重复请求或者用户写相同的信息保存）
     * success：成功
     */
    fun patchUserDescription(id: String, description: String): Int {
        val data = userService.patchUserDescription(id, description)
        var msg = ""
        try {
            thread {
                val body = data.execute().body()!!
                msg = body.msg
                LogRepository.patchUserLog(body, "description")
            }.join(4000)
        } catch (e: Exception) {}
        return when(msg) {
            "existWrong" -> StatusRepository.EXIST_WRONG
            "repeatWrong" -> StatusRepository.SUCCESS
            "success" -> StatusRepository.SUCCESS
            else -> StatusRepository.UNKNOWN_WRONG
        }
    }

    /**
     * msg:
     * existWrong：用户不存在
     * repeatWrong：用户没有修改信息（可能是重复请求或者用户写相同的信息保存）
     * success：成功
     */
    fun patchUserSex(id: String, sex: String): Int {
        val data = userService.patchUserSex(id, sex)
        var msg = ""
        try {
            thread {
                val body = data.execute().body()!!
                msg = body.msg
                LogRepository.patchUserLog(body, "sex")
            }.join(4000)
        } catch (e: Exception) {}
        return when(msg) {
            "existWrong" -> StatusRepository.EXIST_WRONG
            "repeatWrong" -> StatusRepository.SUCCESS
            "success" -> StatusRepository.SUCCESS
            else -> StatusRepository.UNKNOWN_WRONG
        }
    }

    /**
     * msg:
     * existWrong：用户不存在
     * repeatWrong：用户没有修改信息（可能是重复请求或者用户写相同的信息保存）
     * success：成功
     */
    fun patchUserUsername(id: String, username: String): Int {
        val data = userService.patchUserUsername(id, username)
        var msg = ""
        try {
            thread {
                val body = data.execute().body()!!
                msg = body.msg
                LogRepository.patchUserLog(body, "username")
            }.join(4000)
        } catch (e: Exception) {}
        return when(msg) {
            "existWrong" -> StatusRepository.EXIST_WRONG
            "repeatWrong" -> StatusRepository.SUCCESS
            "success" -> StatusRepository.SUCCESS
            else -> StatusRepository.UNKNOWN_WRONG
        }
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

    /***
     * msg:
     * existWrong：用户不存在
     * repeatWrong：已经关注了（可能是重复请求）
     * success：成功
     */
    fun postFans(fromId: String, toId: String): Int {
        val data = userService.postFans(fromId, toId)
        var msg = ""
        try {
            thread {
                val body = data.execute().body()!!
                msg = body.msg
                LogRepository.postFansLog(body)
            }.join(4000)
        } catch (e: Exception) {}
        return when (msg) {
            "existWrong" -> StatusRepository.EXIST_WRONG
            "repeatWrong" -> StatusRepository.REPEAT_WRONG
            "success" -> StatusRepository.SUCCESS
            else -> StatusRepository.UNKNOWN_WRONG
        }
    }

    /***
     * msg:
     * existWrong：用户不存在
     * repeatWrong：已经取关了（可能是重复请求）
     * success：成功
     */
    fun deleteFans(fromId: String, toId: String): Int {
        val data = userService.deleteFans(fromId, toId)
        var msg = ""
        try {
            thread {
                val body = data.execute().body()!!
                msg = body.msg
                LogRepository.deleteFansLog(body)
            }.join(4000)
        } catch (e: Exception) {}
        return when (msg) {
            "existWrong" -> StatusRepository.EXIST_WRONG
            "repeatWrong" -> StatusRepository.REPEAT_WRONG
            "success" -> StatusRepository.SUCCESS
            else -> StatusRepository.UNKNOWN_WRONG
        }
    }

    /***
     * msg:
     * success：成功
     * data.status :  返回status 1：未关注 2：已关注 3：已相互关注
     */
    fun postJudgeFavor(fromId: String, toId: String): Int {
        val data = userService.postJudgeFavor(fromId, toId)
        var msg = ""
        try {
            thread {
                val body = data.execute().body()!!
                followStatus = body.data.status
                msg = body.msg
                LogRepository.postJudgeFavorLog(body)
            }.join(4000)
        } catch (e: Exception) {}
        return when (msg) {
            "success" -> StatusRepository.SUCCESS
            else -> StatusRepository.UNKNOWN_WRONG
        }
    }

    /***
     * msg:
     * success：成功 （返回json followList（关注信息列表） pages（页面总数） counts（数据总量））
     */
    fun getFollow(id: String, cnt: Int, page: Int, keyword: String): Int {
        val data = userService.getFollow(cnt, id, keyword, page)
        var msg = ""
        try {
            thread {
                val body = data.execute().body()!!
                msg = body.msg
                if (msg == "success"){
                    followList.clear()
                    followList.addAll(body.data.followList)
                    followPage = body.data.pages
                    followCount = body.data.counts
                }
                LogRepository.getFollowLog(body)
            }.join(4000)
        } catch (e: Exception) {}
        return when (msg) {
            "success" -> StatusRepository.SUCCESS
            else -> StatusRepository.UNKNOWN_WRONG
        }
    }

    /***
     * msg:
     * success：成功 （返回json fansList（粉丝信息列表） pages（页面总数） counts（数据总量））
     */
    fun getFans(id: String, cnt: Int, page: Int, keyword: String): Int {
        val data = userService.getFans(cnt, id, keyword, page)
        var msg = ""
        try {
            thread {
                val body = data.execute().body()!!
                msg = body.msg
                if (msg == "success"){
                    fansList.clear()
                    fansList.addAll(body.data.fansList)
                    fansPage = body.data.pages
                    fansCount = body.data.counts
                }
                LogRepository.getFansLog(body)
            }.join(4000)
        } catch (e: Exception) {}
        return when (msg) {
            "success" -> StatusRepository.SUCCESS
            else -> StatusRepository.UNKNOWN_WRONG
        }
    }
}