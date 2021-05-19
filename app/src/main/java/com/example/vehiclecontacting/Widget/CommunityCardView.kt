package com.example.vehiclecontacting.Widget

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.vehiclecontacting.R
import kotlinx.android.synthetic.main.view_communitycard.view.*

class CommunityCardView: LinearLayout {

    constructor(context:Context): super(context)
    constructor(context:Context, attrs: AttributeSet): super(context, attrs)
    constructor(context:Context, title: String, avt: Drawable, username: String, text: String,
                img: Drawable, like: Int, comment: Int): super(context){
        card_title.text = title
        card_avt.setAvt(avt)
        card_username.text = username
        card_text.text = text
        card_img.setImageDrawable(img)
        card_status.text = "$like 赞同 · $comment 评论"
    }
    constructor(context:Context, attrs: AttributeSet, title: String, avt: Drawable, username: String, text: String,
        img: Drawable, like: Int, comment: Int): super(context, attrs){
        card_title.text = title
        card_avt.setAvt(avt)
        card_username.text = username
        card_text.text = text
        card_img.setImageDrawable(img)
        card_status.text = "$like 赞同 · $comment 评论"
    }
    init {
        LayoutInflater.from(context).inflate(R.layout.view_communitycard, this)
    }
}