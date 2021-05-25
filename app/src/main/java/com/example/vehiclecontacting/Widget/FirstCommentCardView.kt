package com.example.vehiclecontacting.Widget

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.vehiclecontacting.R
import kotlinx.android.synthetic.main.view_comment_first.view.*

class FirstCommentCardView: LinearLayout {

    constructor(context: Context): super(context)
    constructor(context: Context, attrs: AttributeSet): super(context, attrs)
    constructor(context: Context, avt: String, username: String, text: String, date: String, likeCount: Int) : super(context) {
        comment_first_avt.setAvt(avt)
        comment_first_username.text = username
        comment_first_text.text = text
        comment_first_date.text = date
        comment_first_likeCount.text = likeCount.toString()
    }
    constructor(context: Context, avt: String, username: String, text: String, date: String, likeCount: Int, commentCount: Int) : super(context) {
        comment_first_avt.setAvt(avt)
        comment_first_username.text = username
        comment_first_text.text = text
        comment_first_date.text = date
        comment_first_likeCount.text = likeCount.toString()
        comment_second_count.text = "查看全部 $commentCount 条回复 >"
        comment_second_count.background = resources.getDrawable(R.drawable.bk_comment_second_count)
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.view_comment_first, this)
    }
}