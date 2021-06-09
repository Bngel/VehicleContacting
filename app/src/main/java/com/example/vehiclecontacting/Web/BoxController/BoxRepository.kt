package com.example.vehiclecontacting.Web.BoxController

import com.example.vehiclecontacting.Repository.LogRepository
import com.example.vehiclecontacting.Repository.StatusRepository
import com.example.vehiclecontacting.Web.WebService
import java.lang.Exception
import kotlin.concurrent.thread

object BoxRepository {

    private val boxService = WebService.create()

    val boxList = ArrayList<Box>()

    /***
     * msg:
     * success：成功 （返回json messageList：消息列表 pages：页面总数 counts：数据总量）
     */
    fun getAllBox(cnt: Int, id: String, page: Int, keyword: String = ""): Int {
        val data = boxService.getAllBox(cnt, id, keyword, page)
        var msg = ""
        try {
            thread {
                val body = data.execute().body()!!
                msg = body.msg
                if (msg == "success") {
                    boxList.clear()
                    boxList.addAll(body.data.messageList)
                }
                LogRepository.getAllBoxLog(body)
            }.join(4000)
        } catch (e: Exception) {}
        return when (msg) {
            "success" -> StatusRepository.SUCCESS
            else -> StatusRepository.UNKNOWN_WRONG
        }
    }

    /***
     * msg:
     * existWrong：消息不存在
     * success：成功
     */
    fun deleteBoxMessage(id: String, numbers: List<String>): Int {
        val data = boxService.deleteBoxMessage(id, numbers)
        var msg = ""
        try {
            thread {
                val body = data.execute().body()!!
                msg = body.msg
                LogRepository.deleteBoxMessageLog(body)
            }.join(4000)
        } catch (e: Exception) {}
        return when (msg) {
            "success" -> StatusRepository.SUCCESS
            "existWrong" -> StatusRepository.EXIST_WRONG
            else -> StatusRepository.UNKNOWN_WRONG
        }
    }
}