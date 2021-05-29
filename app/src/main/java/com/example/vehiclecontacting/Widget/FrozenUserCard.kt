package com.example.vehiclecontacting.Widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.vehiclecontacting.R
import kotlinx.android.synthetic.main.view_frozecard.view.*

class FrozenUserCard: LinearLayout {
    constructor(context: Context): super(context)
    constructor(context: Context, attrs: AttributeSet): super(context, attrs)
    constructor(context: Context, avt: String, username: String, frozenDate: String, id: String, vip:Int): super(context) {
        frozeCard_avt.setAvt(avt)
        frozeCard_avt.setVip(vip > 0)
        frozeCard_username.text = username
        frozeCard_frozeDate.text = frozenDate.substring(0, 10)
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.view_frozecard, this)
    }
}