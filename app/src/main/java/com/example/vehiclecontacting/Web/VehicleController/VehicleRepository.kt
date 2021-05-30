package com.example.vehiclecontacting.Web.VehicleController

import com.example.vehiclecontacting.Repository.LogRepository
import com.example.vehiclecontacting.Repository.StatusRepository
import com.example.vehiclecontacting.Web.AdministratorController.AdministratorRepository
import com.example.vehiclecontacting.Web.UserController.PostUserPhoto
import com.example.vehiclecontacting.Web.WebService
import com.google.gson.GsonBuilder
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import java.lang.Exception
import kotlin.concurrent.thread

object VehicleRepository {

    private val vehicleService = WebService.create()

    var vehicleUrl = ""

    /***
     * msg:
     * existWrong：用户不存在
     * fileWrong：文件为空
     * typeWrong：上传格式错误
     * success：成功，成功后返回json：url（图片url）
     */
    fun postVehiclePhoto(id: String, photo: MultipartBody.Part): Int {
        val gson = GsonBuilder()
            .registerTypeAdapter(PostVehiclePhoto::class.java, PostVehiclePhoto.DataStateDeserializer())
            .setLenient()
            .create()
        val photoService = WebService.create(gson)
        val data = photoService.postVehiclePhoto(id, photo)
        var msg = ""
        try {
            thread {
                val body = data.execute().body()!!
                msg = body.msg
                if (msg == "success")
                    vehicleUrl = body.data.toString()
                LogRepository.postVehiclePhotoLog(body)
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
     * repeatWrong：车牌号已被申请
     * existWrong：用户不存在
     * amountWrong：用户上传车辆超过4个
     * success：成功
     */
    fun postVehicle(description: String, id: String, license: String, licensePhoto: String, type: String): Int {
        val data = vehicleService.postVehicle(description, id, license, licensePhoto, type)
        var msg = ""
        try {
            thread {
                val body = data.execute().body()!!
                msg = body.msg
                LogRepository.postVehicleLog(body)
            }.join(4000)
        } catch (e: Exception) {}
        return when (msg) {
            "repeatWrong" -> StatusRepository.REPEAT_WRONG
            "existWrong" -> StatusRepository.EXIST_WRONG
            "amountWrong" -> StatusRepository.AMOUNT_WRONG
            "success" -> StatusRepository.SUCCESS
            else -> StatusRepository.UNKNOWN_WRONG
        }
    }
}