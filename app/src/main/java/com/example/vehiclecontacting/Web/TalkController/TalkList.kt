package com.example.vehiclecontacting.Web.TalkController

data class TalkList(
    val counts: Int,
    val pages: Int,
    val talkMsgList: List<TalkMsg>
)