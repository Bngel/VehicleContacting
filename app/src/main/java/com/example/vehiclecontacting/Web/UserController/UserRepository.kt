package com.example.vehiclecontacting.Web.UserController

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import com.example.vehiclecontacting.Repository.*
import com.example.vehiclecontacting.Web.WebService
import com.example.vehiclecontacting.Widget.ToastView
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

    var isFriend = 0
    val friendList = ArrayList<Friend>()
    val friendApplyList = ArrayList<PerApplyFriend>()

    val searchList = ArrayList<SearchPerUser>()
    var searchPage = 0
    var searchCount = 0

    val postLinkList = ArrayList<LinkUser>()
    val linkList = ArrayList<LinkRelation>()
    var postLinkPage = 0
    var postLinkCount = 0
    var isLink = 0


    private fun getNullUser(msg: String): User {
        return User("",msg,"", "", "",
            "", "", "", -1, -1, -1, -1,
            -1,"", "", "", "", -1, -1, -1, "",-1, -1, -1, -1, -1)
    }

    fun getUser(phone: String): User {
        if (!WebRepository.isNetworkConnected()) {
            ToastView(ActivityCollector.curActivity!!).show("网络错误")
            return getNullUser("")
        }
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
        if (!WebRepository.isNetworkConnected()) {
            ToastView(ActivityCollector.curActivity!!).show("网络错误")
            return getNullUser("")
        }
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
        if (!WebRepository.isNetworkConnected()) {
            ToastView(ActivityCollector.curActivity!!).show("网络错误")
            return StatusRepository.CONNECT_WRONG
        }
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
        if (!WebRepository.isNetworkConnected()) {
            ToastView(ActivityCollector.curActivity!!).show("网络错误")
            return StatusRepository.CONNECT_WRONG
        }
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
        if (!WebRepository.isNetworkConnected()) {
            ToastView(ActivityCollector.curActivity!!).show("网络错误")
            return StatusRepository.CONNECT_WRONG
        }
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
        if (!WebRepository.isNetworkConnected()) {
            ToastView(ActivityCollector.curActivity!!).show("网络错误")
            return StatusRepository.CONNECT_WRONG
        }
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
        if (!WebRepository.isNetworkConnected()) {
            ToastView(ActivityCollector.curActivity!!).show("网络错误")
            return StatusRepository.CONNECT_WRONG
        }
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
        if (!WebRepository.isNetworkConnected()) {
            ToastView(ActivityCollector.curActivity!!).show("网络错误")
            return StatusRepository.CONNECT_WRONG
        }
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
        if (!WebRepository.isNetworkConnected()) {
            ToastView(ActivityCollector.curActivity!!).show("网络错误")
            return StatusRepository.CONNECT_WRONG
        }
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
        if (!WebRepository.isNetworkConnected()) {
            ToastView(ActivityCollector.curActivity!!).show("网络错误")
            return StatusRepository.CONNECT_WRONG
        }
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
        if (!WebRepository.isNetworkConnected()) {
            ToastView(ActivityCollector.curActivity!!).show("网络错误")
            return StatusRepository.CONNECT_WRONG
        }
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
        if (!WebRepository.isNetworkConnected()) {
            ToastView(ActivityCollector.curActivity!!).show("网络错误")
            return StatusRepository.CONNECT_WRONG
        }
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
        if (!WebRepository.isNetworkConnected()) {
            ToastView(ActivityCollector.curActivity!!).show("网络错误")
            return StatusRepository.CONNECT_WRONG
        }
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
        if (!WebRepository.isNetworkConnected()) {
            ToastView(ActivityCollector.curActivity!!).show("网络错误")
            return StatusRepository.CONNECT_WRONG
        }
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
        if (!WebRepository.isNetworkConnected()) {
            ToastView(ActivityCollector.curActivity!!).show("网络错误")
            return StatusRepository.CONNECT_WRONG
        }
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
        if (!WebRepository.isNetworkConnected()) {
            ToastView(ActivityCollector.curActivity!!).show("网络错误")
            return StatusRepository.CONNECT_WRONG
        }
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

    /***
     * msg:
     * blackWrong：被对方拉黑了
     * repeatWrong：已经加好友了
     * success：成功（对方如果已申请加你为好友则会直接加成功）
     */
    fun postFriend(fromId: String, reason: String, toId: String): Int {
        if (!WebRepository.isNetworkConnected()) {
            ToastView(ActivityCollector.curActivity!!).show("网络错误")
            return StatusRepository.CONNECT_WRONG
        }
        val data = userService.postFriend(fromId, reason?:"" , toId)
        var msg = ""
        try {
            thread {
                val body = data.execute().body()!!
                msg = body.msg
                LogRepository.postFriendLog(body)
            }.join(4000)
        } catch (e: Exception) {}
        return when (msg) {
            "success" -> StatusRepository.SUCCESS
            "blackWrong" -> StatusRepository.BLACK_WRONG
            "repeatWrong" -> StatusRepository.REPEAT_WRONG
            else -> StatusRepository.UNKNOWN_WRONG
        }
    }

    /***
     * msg:
     * success：成功 （返回json status：1是 0不是）
     */
    fun postJudgeFriend(fromId: String, toId: String): Int {
        if (!WebRepository.isNetworkConnected()) {
            ToastView(ActivityCollector.curActivity!!).show("网络错误")
            return StatusRepository.CONNECT_WRONG
        }
        val data = userService.postJudgeFriend(fromId, toId)
        var msg = ""
        try {
            thread {
                val body = data.execute().body()!!
                msg = body.msg
                if (msg == "success")
                    isFriend = body.data.status
                LogRepository.postJudgeFriendLog(body)
            }.join(4000)
        } catch (e: Exception) {}
        return when (msg) {
            "success" -> StatusRepository.SUCCESS
            else -> StatusRepository.UNKNOWN_WRONG
        }
    }

    /***
     * msg:
     * success：成功 （返回json friendList：好友列表 pages：页面数 counts：数据总量）
     */
    fun getFriendList(cnt: Int, id: String, page: Int, type: Int): Int {
        if (!WebRepository.isNetworkConnected()) {
            ToastView(ActivityCollector.curActivity!!).show("网络错误")
            return StatusRepository.CONNECT_WRONG
        }
        val data = userService.getFriendList(cnt, id, page, type)
        var msg = ""
        try {
            thread {
                val body = data.execute().body()!!
                msg = body.msg
                if (msg == "success") {
                    friendList.clear()
                    friendList.addAll(body.data.friendList)
                }
                LogRepository.getFriendListLog(body)
            }.join(4000)
        } catch (e: Exception) {}
        return when (msg) {
            "success" -> StatusRepository.SUCCESS
            else -> StatusRepository.UNKNOWN_WRONG
        }
    }

    /***
     * msg:
     * existWrong：好友不存在
     * success：成功
     */
    fun deleteFriend(fromId: String, toId: String): Int {
        if (!WebRepository.isNetworkConnected()) {
            ToastView(ActivityCollector.curActivity!!).show("网络错误")
            return StatusRepository.CONNECT_WRONG
        }
        val data = userService.deleteFriend(fromId, toId)
        var msg = ""
        try {
            thread {
                val body = data.execute().body()!!
                msg = body.msg
                LogRepository.deleteFriendLog(body)
            }.join(4000)
        } catch (e: Exception) {}
        return when (msg) {
            "success" -> StatusRepository.SUCCESS
            "existWrong" -> StatusRepository.EXIST_WRONG
            else -> StatusRepository.UNKNOWN_WRONG
        }
    }

    /***
     * msg:
     * repeatWrong：好友审核已被同意
     * existWrong：好友申请不存在
     * success：成功 申请成功好友数会加1
     */
    fun postVerifyFriend(fromId: String, isPass: Int, toId: String): Int {
        if (!WebRepository.isNetworkConnected()) {
            ToastView(ActivityCollector.curActivity!!).show("网络错误")
            return StatusRepository.CONNECT_WRONG
        }
        val data = userService.postVerifyFriend(fromId, isPass, toId)
        var msg = ""
        try {
            thread {
                val body = data.execute().body()!!
                msg = body.msg
                LogRepository.postVerifyFriendLog(body)
            }.join(4000)
        } catch (e: Exception) {}
        return when (msg) {
            "success" -> StatusRepository.SUCCESS
            "existWrong" -> StatusRepository.EXIST_WRONG
            "repeatWrong" -> StatusRepository.REPEAT_WRONG
            else -> StatusRepository.UNKNOWN_WRONG
        }
    }

    /***
     * msg:
     * success：成功 返回json postFriendList：申请加好友的列表 pages：页面总数 counts：数据总量
     */
    fun getPostFriendList(cnt: Int, id: String, page: Int, type: Int = 1): Int {
        if (!WebRepository.isNetworkConnected()) {
            ToastView(ActivityCollector.curActivity!!).show("网络错误")
            return StatusRepository.CONNECT_WRONG
        }
        val data = userService.getPostFriendList(cnt, id, page, type)
        var msg = ""
        try {
            thread {
                val body = data.execute().body()!!
                msg = body.msg
                if (msg == "success") {
                    friendApplyList.clear()
                    friendApplyList.addAll(body.data.postFriendList)
                }
                LogRepository.getPostFriendListLog(body)
            }.join(4000)
        } catch (e: Exception) {}
        return when (msg) {
            "success" -> StatusRepository.SUCCESS
            else -> StatusRepository.UNKNOWN_WRONG
        }
    }

    /***
     * msg:
     * 会返回一个二维码
     */
    fun getUserQRCode(id: String): Bitmap? {
        if (!WebRepository.isNetworkConnected()) {
            ToastView(ActivityCollector.curActivity!!).show("网络错误")
            return null
        }
        val data = userService.getUserQRCode(id)
        var bitmap: Bitmap? = null
        try {
            thread {
                val body = data.execute().body()!!
                val bitmapArray = Base64.decode(body, Base64.URL_SAFE)
                bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.count())
            }.join(4000)
        } catch (e: Exception) {}
        return bitmap
    }

    /***
     * msg:
     * success：成功 （返回userList：用户列表 pages：页面数 counts：数据总量 ）
     */
    fun getSearchUser(cnt: Int, page: Int, username: String): Int {
        if (!WebRepository.isNetworkConnected()) {
            ToastView(ActivityCollector.curActivity!!).show("网络错误")
            return StatusRepository.CONNECT_WRONG
        }
        val data = userService.getSearchUser(cnt, page, username)
        var msg = ""
        try {
            thread {
                val body = data.execute().body()!!
                msg = body.msg
                if (msg == "success") {
                    searchList.clear()
                    searchList.addAll(body.data.userList)
                    searchPage = body.data.pages
                    searchCount = body.data.counts
                }
                LogRepository.getSearchUserLog(body)
            }.join(4000)
        } catch (e: Exception) {}
        return when (msg) {
            "success" -> StatusRepository.SUCCESS
            else -> StatusRepository.UNKNOWN_WRONG
        }
    }

    /***
     * msg:
     * userWrong：用户连接已达到3个
     * blackWrong：用户在黑名单内
     * repeatWrong：已在连接列表内
     * success：成功
     */
    fun postLinkUser(fromId: String, relationship: String, toId: String): Int {
        if (!WebRepository.isNetworkConnected()) {
            ToastView(ActivityCollector.curActivity!!).show("网络错误")
            return StatusRepository.CONNECT_WRONG
        }
        val data = userService.postLinkUser(fromId, relationship, toId)
        var msg = ""
        try {
            thread {
                val body = data.execute().body()!!
                msg = body.msg
                LogRepository.postLinkUserLog(body)
            }.join(4000)
        } catch (e: Exception) {}
        return when (msg) {
            "success" -> StatusRepository.SUCCESS
            "userWrong" -> StatusRepository.USER_WRONG
            "repeatWrong" -> StatusRepository.REPEAT_WRONG
            "blackWrong" -> StatusRepository.BLACK_WRONG
            else -> StatusRepository.UNKNOWN_WRONG
        }
    }

    /***
     * msg:
     * success：成功 （返回json postUserList：用户列表 pages：页面数 counts：数据总量）
     */
    fun getPostLinkUser(cnt: Int, id: String, page: Int, type: Int = 1): Int {
        if (!WebRepository.isNetworkConnected()) {
            ToastView(ActivityCollector.curActivity!!).show("网络错误")
            return StatusRepository.CONNECT_WRONG
        }
        val data = userService.getPostLinkUser(cnt, id, page, type)
        var msg = ""
        try {
            thread {
                val body = data.execute().body()!!
                msg = body.msg
                if (msg == "success") {
                    postLinkList.clear()
                    postLinkList.addAll(body.data.postUserList)
                    postLinkPage = body.data.pages
                    postLinkCount = body.data.counts
                }
                LogRepository.getPostLinkUserLog(body)
            }.join(4000)
        } catch (e: Exception) {}
        return when (msg) {
            "success" -> StatusRepository.SUCCESS
            else -> StatusRepository.UNKNOWN_WRONG
        }
    }

    /***
     * msg:
     * repeatWrong：对方已经联结
     * existWrong：请求不存在或已被审核
     * userWrong：用户或对方已拥有3个联结用户
     * success：成功
     */
    fun postJudgeLinkUser(fromId: String, isPass: Int, toId: String): Int {
        if (!WebRepository.isNetworkConnected()) {
            ToastView(ActivityCollector.curActivity!!).show("网络错误")
            return StatusRepository.CONNECT_WRONG
        }
        val data = userService.postJudgeLinkUser(fromId, isPass, toId)
        var msg = ""
        try {
            thread {
                val body = data.execute().body()!!
                msg = body.msg
                LogRepository.postJudgeLinkUserLog(body)
            }.join(4000)
        } catch (e: Exception) {}
        return when (msg) {
            "success" -> StatusRepository.SUCCESS
            "userWrong" -> StatusRepository.USER_WRONG
            "repeatWrong" -> StatusRepository.REPEAT_WRONG
            "existWrong" -> StatusRepository.BLACK_WRONG
            else -> StatusRepository.UNKNOWN_WRONG
        }
    }

    /***
     * msg:
     * success：成功 返回linkUserList：联结的用户列表 最多就3个，所以就不分页了~
     */
    fun getLinkUser(id: String): Int {
        if (!WebRepository.isNetworkConnected()) {
            ToastView(ActivityCollector.curActivity!!).show("网络错误")
            return StatusRepository.CONNECT_WRONG
        }
        val data = userService.getLinkUser(id)
        var msg = ""
        try {
            thread {
                val body = data.execute().body()!!
                msg = body.msg
                if (msg == "success") {
                    linkList.clear()
                    linkList.addAll(body.data.linkUserList)
                }
                LogRepository.getLinkUserLog(body)
            }.join(4000)
        } catch (e: Exception) {}
        return when (msg) {
            "success" -> StatusRepository.SUCCESS
            else -> StatusRepository.UNKNOWN_WRONG
        }
    }

    /***
     * msg:
     * existWrong：联结关系不存在
     * success：成功
     */
    fun deleteRemoveLink(fromId: String, toId: String): Int {
        if (!WebRepository.isNetworkConnected()) {
            ToastView(ActivityCollector.curActivity!!).show("网络错误")
            return StatusRepository.CONNECT_WRONG
        }
        val data = userService.deleteRemoveLink(fromId, toId)
        var msg = ""
        try {
            thread {
                val body = data.execute().body()!!
                msg = body.msg
                LogRepository.deleteRemoveLinkLog(body)
            }.join(4000)
        } catch (e: Exception) {}
        return when (msg) {
            "success" -> StatusRepository.SUCCESS
            "existWrong" -> StatusRepository.EXIST_WRONG
            else -> StatusRepository.UNKNOWN_WRONG
        }
    }

    /***
     * msg:
     * success：成功 返回json status：1有 0 没有
     */
    fun getJudgeLink(fromId: String, toId: String): Int {
        if (!WebRepository.isNetworkConnected()) {
            ToastView(ActivityCollector.curActivity!!).show("网络错误")
            return StatusRepository.CONNECT_WRONG
        }
        val data = userService.getJudgeLink(fromId, toId)
        var msg = ""
        try {
            thread {
                val body = data.execute().body()!!
                msg = body.msg
                if (msg == "success")
                    isLink = body.data.status
                LogRepository.getJudgeLinkLog(body)
            }.join(4000)
        } catch (e: Exception) {}
        return when (msg) {
            "success" -> StatusRepository.SUCCESS
            else -> StatusRepository.UNKNOWN_WRONG
        }
    }
}