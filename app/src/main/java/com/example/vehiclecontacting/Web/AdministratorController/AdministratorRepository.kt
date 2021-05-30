package com.example.vehiclecontacting.Web.AdministratorController

import com.example.vehiclecontacting.Repository.LogRepository
import com.example.vehiclecontacting.Repository.StatusRepository
import com.example.vehiclecontacting.Web.WebService
import java.lang.Exception
import kotlin.concurrent.thread

object AdministratorRepository {

    private val adminService = WebService.create()

    const val IS_PASS = 1
    const val NOT_PASS = 2

    val vehicleCards = ArrayList<Vehicle>()
    val frozenList = ArrayList<FrozenUser>()

    /***
     * msg:
     * success：成功 （返回 vehicleList：需要审核车辆大致信息）
     */
    fun getJudgeVehicleList(cnt: Int, keyword: String, page: Int): Int {
        val data = adminService.getVehicleList(cnt, keyword, page)
        var msg = ""
        try {
            thread {
                val body = data.execute().body()!!
                msg = body.msg
                if (msg == "success") {
                    vehicleCards.clear()
                    vehicleCards.addAll(body.data.vehicleList)
                }
                LogRepository.getJudgeVehicleListLog(body)
            }.join(4000)
        } catch (e: Exception) {}
        return when (msg) {
            "success" -> StatusRepository.SUCCESS
            else -> StatusRepository.UNKNOWN_WRONG
        }
    }

    /***
     * msg:
     * existWrong：车辆信息不存在
     * repeatWrong：该车辆信息已被审核（重复提交或者被别的管理员审核了）
     * success：成功
     */
    fun postJudgeVehicle(isPass: Int, license: String, reason: String = ""): Int {
        val data = adminService.postJudgeVehicle(isPass, license, reason)
        var msg = ""
        try {
            thread {
                val body = data.execute().body()!!
                msg = body.msg
                LogRepository.postJudgeVehicleLog(body)
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
     * existWrong：用户不存在
     * success：成功
     */
    fun postFrozeUser(id: String, minutes: Int): Int {
        val data = adminService.postFrozeUser(id, minutes)
        var msg = ""
        try {
            thread {
                val body = data.execute().body()!!
                msg = body.msg
                LogRepository.postFrozeUserLog(body)
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
     * success：成功 （返回json frozenUserList：封禁用户信息列表 pages：页面总数 counts：数据总量）
     */
    fun getFrozenList(cnt: Int, page: Int): Int {
        val data = adminService.getFrozenList(cnt, page)
        var msg = ""
        try {
            thread {
                val body = data.execute().body()!!
                msg = body.msg
                if (msg == "success") {
                    frozenList.clear()
                    frozenList.addAll(body.data.frozenUserList)
                }
                LogRepository.getFrozenListLog(body)
            }.join(4000)
        } catch (e: Exception) {}
        return when (msg) {
            "success" -> StatusRepository.SUCCESS
            else -> StatusRepository.UNKNOWN_WRONG
        }
    }

    /***
     * msg:
     * existWrong：用户不存在
     * repeatWrong：用户已被解封（可能是重复请求）
     * success：成功
     */
    fun postReopenUser(id: String): Int {
        val data = adminService.postReopenUser(id)
        var msg = ""
        try {
            thread {
                val body = data.execute().body()!!
                msg = body.msg
                LogRepository.postReopenUserLog(body)
            }.join(4000)
        } catch (e: Exception) {}
        return when (msg) {
            "success" -> StatusRepository.SUCCESS
            "existWrong" -> StatusRepository.EXIST_WRONG
            "repeatWrong" -> StatusRepository.REPEAT_WRONG
            else -> StatusRepository.UNKNOWN_WRONG
        }
    }
}