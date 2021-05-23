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

    fun postDiscuss(description: String, id: String, title: String,
                    photos: List<String>): Int {
        var photo1 = ""
        var photo2 = ""
        var photo3 = ""
        for (photo in photos.indices) {
            if (photo == 0)
                photo1 = photos[photo]
            else if (photo == 1)
                photo2 = photos[photo]
            else if (photo == 1)
                photo3 = photos[photo]
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
}