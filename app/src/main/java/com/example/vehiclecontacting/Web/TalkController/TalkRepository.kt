package com.example.vehiclecontacting.Web.TalkController

import androidx.lifecycle.MutableLiveData
import com.example.vehiclecontacting.Repository.ActivityCollector
import com.example.vehiclecontacting.Repository.LogRepository
import com.example.vehiclecontacting.Repository.StatusRepository
import com.example.vehiclecontacting.Repository.WebRepository
import com.example.vehiclecontacting.Web.UserController.UserRepository
import com.example.vehiclecontacting.Web.WebService
import com.example.vehiclecontacting.Widget.ToastView
import java.lang.Exception
import kotlin.concurrent.thread

object TalkRepository {

    val updateMessage = MutableLiveData<Boolean>()

    private val talkService = WebService.create()

    val talkList = ArrayList<Talk>()
    val talkBoxList = ArrayList<TalkMsg>()

    /**
     * msg:
     * success：成功 （返回json talkList：聊天列表 counts：数据总量 pages：页面总数）
     */
    fun getTalk(cnt: Int, fromId: String, page: Int, toId: String): Int {
        if (!WebRepository.isNetworkConnected()) {
            ToastView(ActivityCollector.curActivity!!).show("网络错误")
            return StatusRepository.CONNECT_WRONG
        }
        val data = talkService.getTalk(cnt, fromId, page, toId)
        var msg = ""
        try {
            thread {
                val body = data.execute().body()!!
                msg = body.msg
                if (msg == "success") {
                    talkList.clear()
                    talkList.addAll(body.data.talkList)
                }
                LogRepository.getTalkLog(body)
            }.join(4000)
        } catch (e: Exception) {}
        return when(msg) {
            "success" -> StatusRepository.SUCCESS
            else -> StatusRepository.UNKNOWN_WRONG
        }
    }

    /**
     * msg:
     * success：成功 （返回json talkMsgList：聊天列表 pages：页面总数 counts：数据量）
     */
    fun getTalkList(cnt: Int, id: String, page: Int): Int {
        if (!WebRepository.isNetworkConnected()) {
            ToastView(ActivityCollector.curActivity!!).show("网络错误")
            return StatusRepository.CONNECT_WRONG
        }
        val data = talkService.getTalkList(cnt, id, page)
        var msg = ""
        try {
            thread {
                val body = data.execute().body()!!
                msg = body.msg
                if (msg == "success") {
                    talkBoxList.clear()
                    talkBoxList.addAll(body.data.talkMsgList)
                }
                LogRepository.getTalkListLog(body)
            }.join(4000)
        } catch (e: Exception) {}
        return when(msg) {
            "success" -> StatusRepository.SUCCESS
            else -> StatusRepository.UNKNOWN_WRONG
        }
    }

    /**
     * msg:
     * existWrong：该消息不存在（可能时重复请求）
     * success：成功
     */
    fun deleteTalk(fromId: String, toId: String): Int {
        if (!WebRepository.isNetworkConnected()) {
            ToastView(ActivityCollector.curActivity!!).show("网络错误")
            return StatusRepository.CONNECT_WRONG
        }
        val data = talkService.deleteTalk(fromId, toId)
        var msg = ""
        try {
            thread {
                val body = data.execute().body()!!
                msg = body.msg
                LogRepository.deleteTalkLog(body)
            }.join(4000)
        } catch (e: Exception) {}
        return when(msg) {
            "success" -> StatusRepository.SUCCESS
            "existWrong" -> StatusRepository.EXIST_WRONG
            else -> StatusRepository.UNKNOWN_WRONG
        }
    }
}