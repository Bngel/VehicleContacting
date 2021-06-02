package com.example.vehiclecontacting.Widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.example.vehiclecontacting.R
import kotlinx.android.synthetic.main.dialog_addfriend.view.*
import kotlinx.android.synthetic.main.dialog_vehicle.view.*

class AddFriendDialogView: LinearLayout {
    constructor(context: Context): super(context)
    constructor(context: Context, attrs: AttributeSet): super(context, attrs)
    constructor(context: Context, username: String): super(context) {
        dialogFriend_msg.text = "是否申请添加用户 $username 为好友"
    }
    init {
        LayoutInflater.from(context).inflate(R.layout.dialog_addfriend, this)
    }
}