package com.example.vehiclecontacting.Web.DiscussController

import com.example.vehiclecontacting.CreateActivity
import com.example.vehiclecontacting.LogRepository
import com.example.vehiclecontacting.StatusRepository
import com.example.vehiclecontacting.Web.UserController.PostUserPhoto
import com.example.vehiclecontacting.Web.WebService
import com.google.gson.GsonBuilder
import okhttp3.MultipartBody
import java.lang.Exception
import kotlin.concurrent.thread

object DiscussRepository {

    private val discussService = WebService.create()
    val imageUrl = ArrayList<String>()
    val discussList = ArrayList<Discuss>()
    val commentList = ArrayList<Comment>()
    lateinit var ownerComment: OwnerComment
    var pageCount = 0

    /***
     * msg:
     * fileWrong：文件为空
     * typeWrong：文件类型错误
     * success：成功（返回json带url）
     */
    fun postDiscussPhoto(photo: MultipartBody.Part): Int {
        val gson = GsonBuilder()
            .registerTypeAdapter(PostDiscussPhoto::class.java, PostDiscussPhoto.DataStateDeserializer())
            .setLenient()
            .create()
        val photoService = WebService.create(gson)
        val data = photoService.postDiscussPhoto(photo)
        var msg = ""
        try {
            thread {
                val body = data.execute().body()!!
                msg = body.msg
                LogRepository.updateDiscussPhotoLog(body)
                if (msg == "success")
                    imageUrl.add(body.data as String)
            }.join(4000)
        } catch (e: Exception) {}
        return when (msg) {
            "fileWrong" -> StatusRepository.FILE_WRONG
            "typeWrong" -> StatusRepository.TYPE_WRONG
            "success" -> StatusRepository.SUCCESS
            else -> StatusRepository.UNKNOWN_WRONG
        }
    }

    /***
     * msg:
     * repeatWrong：该用户短期创建新帖子太多（2小时5次以上）
     * success：成功（返回json带url）
     */
    fun postDiscuss(description: String, id: String, title: String,
                    photo1: String = "", photo2: String = "", photo3: String = ""): Int {
        val data = discussService.postDiscuss(description, id, photo1, photo2, photo3, title)
        var msg = ""
        try {
            thread {
                val body = data.execute().body()!!
                msg = body.msg
                LogRepository.sendDiscussLog(body)
            }.join(4000)
        } catch (e: Exception) {}
        return when (msg) {
            "repeatWrong" -> StatusRepository.TYPE_WRONG
            "success" -> StatusRepository.SUCCESS
            else -> StatusRepository.UNKNOWN_WRONG
        }
    }

    /***
     * msg:
     * repeatWrong：该用户短期创建新帖子太多（2小时5次以上）
     * success：成功（返回json带url）
     */
    fun postDiscuss(description: String, id: String, title: String,
                    photos: List<String>): Int {
        var photo1 = ""
        var photo2 = ""
        var photo3 = ""
        for (photo in photos.indices) {
            when (photo) {
                0 -> photo1 = photos[photo]
                1 -> photo2 = photos[photo]
                2 -> photo3 = photos[photo]
            }
        }
        val data = discussService.postDiscuss(description, id, photo1, photo2, photo3, title)
        var msg = ""
        try {
            thread {
                val body = data.execute().body()!!
                msg = body.msg
                LogRepository.sendDiscussLog(body)
            }.join(4000)
        } catch (e: Exception) {}
        return when (msg) {
            "repeatWrong" -> StatusRepository.TYPE_WRONG
            "success" -> StatusRepository.SUCCESS
            else -> StatusRepository.UNKNOWN_WRONG
        }
    }

    /***
     * msg:
     * success：成功（返回json discussList（主页帖子信息列表） pages（页面总数） counts（帖子总数））
     */
    fun getDiscuss(cnt: Int, isOrderByTime: Int, page: Int, isFollow: Int, keyword: String = "", id: String = ""): Int {
        val data = discussService.getDiscuss(id, cnt,isOrderByTime, keyword, page, isFollow)
        var msg = ""
        try {
            thread {
                val body = data.execute().body()!!
                msg = body.msg
                discussList.addAll(body.data.discussList)
                pageCount = body.data.pages
                LogRepository.getDiscussLog(body)
            }.join(4000)
        } catch (e: Exception) {}
        return when (msg) {
            "success" -> StatusRepository.SUCCESS
            else -> StatusRepository.UNKNOWN_WRONG
        }
    }

    /***
     * msg:
     * existWrong：帖子不存在（可能是重复删除）
     * userWrong：用户不是帖子的主人
     * success：成功
     */
    fun deleteDiscuss(id: String, number: String): Int {
        val data = discussService.deleteDiscuss(id, number)
        var msg = ""
        try {
            thread {
                val body = data.execute().body()!!
                msg = body.msg
                LogRepository.deleteDiscussLog(body)
            }.join(4000)
        } catch (e: Exception) {}
        return when (msg) {
            "existWrong" -> StatusRepository.EXIST_WRONG
            "userWrong" -> StatusRepository.USER_WRONG
            "success" -> StatusRepository.SUCCESS
            else -> StatusRepository.UNKNOWN_WRONG
        }
    }

    /***
     * msg:
     * existWrong：帖子不存在（可能被删了，提示一下）
     * success：成功（返回json ownerComment（楼主的评论和帖子的相关信息） pages（页面总数） counts（帖子总数） commentList（评论列表））
     */
    fun getComment(cnt: Int, isOrderByTime: Int, page: Int, number: String): Int {
        val data = discussService.getComment(cnt,isOrderByTime, number, page)
        var msg = ""
        try {
            thread {
                val body = data.execute().body()!!
                msg = body.msg
                commentList.addAll(body.data.commentList)
                ownerComment = body.data.OwnerComment
                pageCount = body.data.pages
                LogRepository.getCommentLog(body)
            }.join(4000)
        } catch (e: Exception) {}
        return when (msg) {
            "existWrong" -> StatusRepository.EXIST_WRONG
            "success" -> StatusRepository.SUCCESS
            else -> StatusRepository.UNKNOWN_WRONG
        }
    }


}