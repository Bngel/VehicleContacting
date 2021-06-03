package com.example.vehiclecontacting.Widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.vehiclecontacting.R
import com.example.vehiclecontacting.Repository.InfoRepository
import com.example.vehiclecontacting.Repository.StatusRepository
import com.example.vehiclecontacting.Web.UserController.UserRepository
import kotlinx.android.synthetic.main.view_friendapplycard.view.*

class FriendApplyCardView: LinearLayout {

    constructor(context: Context): super(context)
    constructor(context: Context, attrs: AttributeSet): super(context, attrs)
    constructor(context: Context, id: String, avt: String, username: String, description: String): super(context) {
        friendapplyCard_avt.setAvt(avt)
        friendapplyCard_username.text = username
        friendapplyCard_description.text = description
    }
    init {
        LayoutInflater.from(context).inflate(R.layout.view_friendapplycard, this)
    }
}