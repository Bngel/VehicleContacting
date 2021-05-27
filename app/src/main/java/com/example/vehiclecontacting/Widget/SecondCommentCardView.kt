package com.example.vehiclecontacting.Widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.vehiclecontacting.R
import com.example.vehiclecontacting.Web.DiscussController.CommentOwner
import kotlinx.android.synthetic.main.view_comment_second.view.*

class SecondCommentCardView: LinearLayout{

    constructor(context: Context): super(context)
    constructor(context: Context, attrs: AttributeSet): super(context, attrs)
    constructor(context: Context, avt: String, username: String, text: String, date: String, likeCount: String) : super(context) {
        comment_second_avt.setAvt(avt)
        comment_second_username.text = username
        comment_second_text.text = text
        comment_second_date.text = date
        comment_second_likeCount.text = likeCount
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.view_comment_second, this)
    }

    fun setData(avt: String, username: String, text: String, date: String, likeCount: String) {
        comment_second_avt.setAvt(avt)
        comment_second_username.text = username
        comment_second_text.text = text
        comment_second_date.text = date
        comment_second_likeCount.text = likeCount
    }

    fun setData(data: CommentOwner) {
        comment_second_avt.setAvt(data.photo)
        comment_second_username.text = data.username
        comment_second_text.text = data.comments
        comment_second_likeCount.text = data.likeCounts.toString()
        comment_second_date.text = data.createTime.substring(0,10)
    }
}
