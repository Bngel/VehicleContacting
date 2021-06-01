package com.example.vehiclecontacting.Web

import java.net.URI

object WebRepository {
    private const val baseUri = "wss://47.115.128.193/websocket/"

    lateinit var webClient: JWebSocketClient

    fun createWebClient(id: String) {
        webClient = JWebSocketClient(URI.create("$baseUri$id"))
        webClient.connectBlocking()
    }
}