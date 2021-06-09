package com.example.vehiclecontacting.Web

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.vehiclecontacting.Activity.ChatActivity
import com.example.vehiclecontacting.Activity.ChatBoxActivity
import com.example.vehiclecontacting.Activity.PushActivity
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
    val CHANNEL_ID = 0x00

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
                    if (id == (ActivityCollector.curActivity as ChatActivity).userId){
                        TalkRepository.updateMessage.postValue(true)
                    }
                }
                else {
                    val data = JSONObject(json.get("data").toString())
                    val username = data.get("username").toString()
                    val info = data.get("info").toString()
                    createNotification(ActivityCollector.curActivity!!, "${username}发来了一条消息", info, 2)
                }
            }
            "systemInfoSuccess" -> {
                val data = JSONObject(json.get("data").toString())
                val title = data.get("title").toString()
                val content = data.get("content").toString()
                createNotification(ActivityCollector.curActivity!!, title, content, 1)
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

    private fun createNotification(context: Context, title: String, content: String, type: Int) {
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel("notification01", "vehiclePush", NotificationManager.IMPORTANCE_DEFAULT)
        // Create the TaskStackBuilder
        val resultPendingIntent: PendingIntent? = TaskStackBuilder.create(context).run {
            // Add the intent, which inflates the back stack
            addNextIntentWithParentStack(when (type){
                1 -> Intent(context, PushActivity::class.java)
                2 -> Intent(context, ChatBoxActivity::class.java)
                else -> Intent(context, PushActivity::class.java)
            })
            // Get the PendingIntent containing the entire back stack
            getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        manager.createNotificationChannel(channel)
        val builder = NotificationCompat.Builder(context, "notification01")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(content)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(resultPendingIntent)
            .setAutoCancel(true)
        with(NotificationManagerCompat.from(context)) {
            // notificationId is a unique int for each notification that you must define
            notify(CHANNEL_ID, builder.build())
        }
    }

}