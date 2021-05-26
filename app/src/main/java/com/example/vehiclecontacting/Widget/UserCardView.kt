package com.example.vehiclecontacting.Widget

import android.content.ClipDescription
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.vehiclecontacting.R
import kotlinx.android.synthetic.main.view_usercard.view.*

class UserCardView: LinearLayout {

    constructor(context: Context): super(context)
    constructor(context: Context, attrs: AttributeSet): super(context, attrs)
    constructor(context: Context, id:String, avt: String, username: String, description: String): super(context) {
        userCard_avt.setAvt(avt)
        userCard_username.text = username
        userCard_description.text = description
    }
    init {
        LayoutInflater.from(context).inflate(R.layout.view_usercard, this)
    }
}