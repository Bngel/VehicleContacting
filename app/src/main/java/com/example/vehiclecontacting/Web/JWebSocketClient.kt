package com.example.vehiclecontacting.Web

import android.util.Log
import com.example.vehiclecontacting.Repository.LogRepository
import com.example.vehiclecontacting.Repository.StatusRepository
import org.java_websocket.client.WebSocketClient
import org.java_websocket.drafts.Draft_6455
import org.java_websocket.handshake.ServerHandshake
import java.lang.Exception
import java.net.URI

class JWebSocketClient(serverUri: URI): WebSocketClient(serverUri, Draft_6455()) {
    override fun onOpen(handshakedata: ServerHandshake?) {
        LogRepository.webSocketStatusLog("onOpen")
    }

    override fun onMessage(message: String?) {
        LogRepository.webSocketStatusLog("onMessage")
    }

    override fun onClose(code: Int, reason: String?, remote: Boolean) {
        LogRepository.webSocketStatusLog("onClose")
    }

    override fun onError(ex: Exception?) {
        LogRepository.webSocketStatusLog("onError")
    }

}