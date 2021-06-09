package com.example.vehiclecontacting.Widget

import android.content.Context
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.vehiclecontacting.R
import kotlinx.android.synthetic.main.dialog_box.view.*

class BoxDialogView: LinearLayout {
    constructor(context: Context): super(context)
    constructor(context: Context, title: String, content: String): super(context) {
        dialogBox_title.text = title
        dialogBox_msg.text = content
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.dialog_box, this)
    }
}