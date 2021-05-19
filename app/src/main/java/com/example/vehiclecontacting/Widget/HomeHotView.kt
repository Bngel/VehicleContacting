package com.example.vehiclecontacting.Widget

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.vehiclecontacting.Data.HotInfo
import com.example.vehiclecontacting.R
import kotlinx.android.synthetic.main.view_homehot.view.*

class HomeHotView:LinearLayout {

    constructor(context: Context): super(context)
    constructor(context: Context, attrs: AttributeSet): super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, title: String, type: String, Img: Drawable): super(context, attrs) {
        hot_title.text = title
        hot_type.text = type
        hot_img.setImageDrawable(Img)
    }
    constructor(context: Context, title: String, type: String, Img: Drawable): super(context) {
        hot_title.text = title
        hot_type.text = type
        hot_img.setImageDrawable(Img)
    }
    constructor(context: Context, title: String, type: String, Img: Int): super(context) {
        hot_title.text = title
        hot_type.text = type
        hot_img.setImageResource(Img)
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.view_homehot, this)
    }
}