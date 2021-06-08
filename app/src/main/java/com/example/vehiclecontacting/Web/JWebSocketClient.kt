package com.example.vehiclecontacting.Web

import android.util.Log
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.vehiclecontacting.Activity.ChatActivity
import com.example.vehiclecontacting.R
import com.example.vehiclecontacting.Repository.*
import com.example.vehiclecontacting.Web.TalkController.TalkRepository
import com.example.vehiclecontacting.Widget.ChatLeftView
import kotlinx.android.synthetic.main.activity_chat.view.*
import org.java_websocket.client.WebSocketClient
import org.java_websocket.drafts.Draft_6455
import org.java_websocket.enums.ReadyState
import org.java_websocket.handshake.ServerHandshake
import org.json.JSONObject
import java.lang.Exception
import java.net.URI
import kotlin.concurrent.thread

class JWebSocketClient(serverUri: URI): WebSocketClient(serverUri, Draft_6455()) {
    override fun onOpen(handshakedata: ServerHandshake?) {
        LogRepository.webSocketStatusLog("onOpen")
    }

    override fun onMessage(message: String?) {
        LogRepository.webSocketStatusLog("onMessage", message?:"")
        val json = JSONObject(message?:"")
        val msg = json.get("msg")
        when (msg) {
            "sendInfoSuccess" -> {}
            "receiveInfoSuccess" -> {
                val data = JSONObject(json.get("data").toString())
                val id = data.get("id").toString()
                if (ActivityCollector.curActivity?.javaClass == ChatActivity::class.java){
                    Log.d(StatusRepository.VehicleLog, (id == (ActivityCollector.curActivity as ChatActivity).userId).toString())
                    if (id == (ActivityCollector.curActivity as ChatActivity).userId){
                        TalkRepository.updateMessage.postValue(true)
                    }
                }
            }
            else -> {}
        }
    }

    override fun onClose(code: Int, reason: String?, remote: Boolean) {
        LogRepository.webSocketStatusLog("onClose")
    }

    override fun onError(ex: Exception?) {
        LogRepository.webSocketStatusLog("onError")
        ex?.printStackTrace()
        if (InfoRepository.loginStatus.status) {
            WebRepository.createWebClient(InfoRepository.user!!.id)
            while (WebRepository.webClient.readyState != ReadyState.OPEN)
                Log.d(StatusRepository.VehicleLog, "连接中")
        }
    }

}