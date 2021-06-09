package com.example.vehiclecontacting.Widget

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.example.vehiclecontacting.R
import kotlinx.android.synthetic.main.view_chatbox.view.*

class ChatBoxView: LinearLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, avt: String, username: String, content: String, notRead: Int): super(context) {
        chatBox_avt.setAvt(avt)
        chatBox_avt.setVip(false)
        chatBox_username.text = username
        chatBox_content.text = content
        if (notRead > 0) {
            chatBox_attention.visibility = View.VISIBLE
            chatBox_attention.text = if (notRead > 99) "99+" else notRead.toString()
        }
        else {
            chatBox_attention.visibility = View.INVISIBLE
        }
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.view_chatbox, this)
    }
}