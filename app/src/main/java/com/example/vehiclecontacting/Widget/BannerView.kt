package com.example.vehiclecontacting.Widget

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.RelativeLayout
import com.bumptech.glide.Glide
import com.example.vehiclecontacting.R
import kotlinx.android.synthetic.main.view_banner.view.*

class BannerView: RelativeLayout {

    constructor(context: Context, attrs: AttributeSet): super(context, attrs)
    constructor(context: Context): super(context)

    init {
        LayoutInflater.from(context).inflate(R.layout.view_banner, this)
        banner_img.scaleType = ImageView.ScaleType.CENTER_CROP
    }

    fun setImageResource(image: Int) {
        banner_img.setImageResource(image)
    }

    fun setImageResource(image: Drawable){
        banner_img.setImageDrawable(image)
    }

    fun setImageResource(image: String) {
        Glide.with(context)
            .load(image)
            .into(banner_img)
    }

    fun setText(text: String) {
        banner_text.text = text
    }
}