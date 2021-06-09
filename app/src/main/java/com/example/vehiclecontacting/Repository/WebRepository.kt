package com.example.vehiclecontacting.Repository

import com.example.vehiclecontacting.Web.JWebSocketClient
import java.net.URI

object WebRepository {
    private const val baseUri = "ws://47.115.128.193:8090/websocket/"

    lateinit var webClient: JWebSocketClient

    fun createWebClient(id: String) {
        webClient = JWebSocketClient(URI.create("$baseUri$id"))
        webClient.connectBlocking()
    }
}