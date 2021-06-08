package com.example.vehiclecontacting.Widget

import android.content.Context
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.vehiclecontacting.R
import kotlinx.android.synthetic.main.view_chat_right.view.*

class ChatRightView: LinearLayout {
    constructor(context: Context): super(context)
    constructor(context: Context, avt: String, msg: String): super(context) {
        chat_right_avt.setAvt(avt)
        chat_right_avt.setVip(false)
        chat_right_msg.text = msg
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.view_chat_right, this)
    }
}