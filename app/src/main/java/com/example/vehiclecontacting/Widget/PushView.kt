package com.example.vehiclecontacting.Widget

import android.content.Context
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.vehiclecontacting.R
import kotlinx.android.synthetic.main.view_push.view.*

class PushView: LinearLayout {

    constructor(context: Context): super(context)
    constructor(context: Context, title: String, content: String): super(context) {
        push_title.text = title
        push_content.text = content
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.view_push, this)
    }
}