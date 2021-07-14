package com.example.vehiclecontacting.Repository

import android.content.Context
import android.net.ConnectivityManager
import com.example.vehiclecontacting.Web.JWebSocketClient
import java.net.URI


object WebRepository {
    private const val baseUri = "wss://www.rat403.cn/websocket/"

    lateinit var webClient: JWebSocketClient

    fun createWebClient(id: String) {
        webClient = JWebSocketClient(URI.create("$baseUri$id"))
        webClient.connectBlocking()
    }

    fun isNetworkConnected(context: Context?): Boolean {
        if (context != null) {
            val mConnectivityManager = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val mNetworkInfo = mConnectivityManager.activeNetworkInfo
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable
            }
        }
        return false
    }

    fun isNetworkConnected(): Boolean {
        val context = ActivityCollector.curActivity
        if (context != null) {
            val mConnectivityManager = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val mNetworkInfo = mConnectivityManager.activeNetworkInfo
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable
            }
        }
        return false
    }

}