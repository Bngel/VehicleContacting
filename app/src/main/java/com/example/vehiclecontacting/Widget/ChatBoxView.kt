package com.example.vehiclecontacting.Widget

import android.content.Context
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.vehiclecontacting.R
import kotlinx.android.synthetic.main.view_chatbox.view.*

class ChatBoxView: LinearLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, avt: String, username: String, content: String): super(context) {
        chatBox_avt.setAvt(avt)
        chatBox_avt.setVip(false)
        chatBox_username.text = username
        chatBox_content.text = content
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.view_chatbox, this)
    }
}